package pe.edu.upc.backendfinanzas.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backendfinanzas.dtos.request.CreditoRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.CreditoResponseDTO;
import pe.edu.upc.backendfinanzas.entities.*;
import pe.edu.upc.backendfinanzas.repositories.CreditoRepository;
import pe.edu.upc.backendfinanzas.repositories.EntidadFinancieraRepository;
import pe.edu.upc.backendfinanzas.services.CreditoService;
import pe.edu.upc.backendfinanzas.services.EntidadFinancieraService;
import pe.edu.upc.backendfinanzas.services.InmuebleService;

import java.math.MathContext;
import java.math.RoundingMode;


import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditoServiceImpl implements CreditoService {
    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private EntidadFinancieraService entidadFinancieraService;

    @Autowired
    private InmuebleService inmuebleService;

    @Autowired
    private EntidadFinancieraRepository entidadFinancieraRepository;

    @Override
    public List<Credito> findAll() {
        return creditoRepository.findAll();
    }

    @Override
    public Credito findById(int id) {
        return creditoRepository.findById(id).orElse(null);
    }

    @Override
    public Credito insert(Credito credito) {
        return creditoRepository.save(credito);
    }

    @Override
    public Credito update(Credito credito) {
        return creditoRepository.save(credito);
    }

    @Override
    public void delete(int id) {
        creditoRepository.deleteById(id);
    }

    @Override
    public List<Credito> findByUsuarioId(int usuarioId) {
        return creditoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Credito> findByMontoPrestamoBetween(BigDecimal min, BigDecimal max) {
        return creditoRepository.findByMontoPrestamoBetween(min, max);
    }

    @Override
    public List<Credito> findByDepartamentoOProvincia(String departamento, String provincia) {
        return creditoRepository.findByDepartamentoOProvincia(departamento, provincia);
    }

    @Override
    public List<Credito> findCreditosSobrePromedio() {
        return creditoRepository.findCreditosSobrePromedio();
    }

    @Override
    public CreditoResponseDTO calcularTasa(CreditoRequestDTO dto) {
        int idEntidadFinanciera = dto.getIdEntidadFinanciera();
        int idInmueble = dto.getIdInmueble();

        TipoTasaInteres tipoTasaInteres = dto.getTipoTasaInteres();
        FrecuenciaPago frecuenciaPago = dto.getFrecuenciaPago();

        EntidadFinanciera entidadFinanciera = entidadFinancieraService.listId(idEntidadFinanciera);
        Inmueble inmueble = inmuebleService.listId(idInmueble);
        TipoVivienda tipo = inmueble.getTipoVivienda();

        BigDecimal nuevaTea = entidadFinanciera.getTea();

        BigDecimal tasaFinal = BigDecimal.ZERO;
        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);

        int capitalizacion = 30;
        if(tipoTasaInteres == TipoTasaInteres.NOMINAL){
            if(frecuenciaPago != null){
                if (frecuenciaPago == FrecuenciaPago.MENSUAL) {
                    capitalizacion = 30;
                }
                else if (frecuenciaPago == FrecuenciaPago.BIMESTRAL) {
                    capitalizacion = 60;
                }
                else if (frecuenciaPago == FrecuenciaPago.TRIMESTRAL) {
                    capitalizacion = 90;
                }
                else if (frecuenciaPago == FrecuenciaPago.SEMESTRAL) {
                    capitalizacion = 180;
                }
                else {
                    capitalizacion = 360;
                }
            }
            // Conversión a nominal (ejemplo simplificado)
            BigDecimal m = BigDecimal.valueOf(360.0 / capitalizacion);
            BigDecimal n = BigDecimal.valueOf(30.0 / capitalizacion);
            BigDecimal parte1 = nuevaTea.divide(BigDecimal.valueOf(100), mc).divide(m, mc);
            BigDecimal parte2 = BigDecimal.ONE.add(parte1);

            double pow = Math.pow(parte2.doubleValue(), n.doubleValue());
            tasaFinal = BigDecimal.valueOf(pow - 1).multiply(BigDecimal.valueOf(100));
        }
        else {
            // Cuando es EFECTIVO se calcula a TEM
            BigDecimal diasTEM = BigDecimal.valueOf(30);
            BigDecimal diasTEA = BigDecimal.valueOf(360);

            BigDecimal parte1 = BigDecimal.ONE.add(nuevaTea.divide(BigDecimal.valueOf(100), mc));
            BigDecimal parte2 = diasTEM.divide(diasTEA, mc);

            double pow = Math.pow(parte1.doubleValue(), parte2.doubleValue());
            tasaFinal = BigDecimal.valueOf(pow - 1).multiply(BigDecimal.valueOf(100));
        }
        System.out.println("Tasa Final Calculada: " + tasaFinal + "%");

        BigDecimal previoVenta = inmueble.getPrecioVenta();
        BigDecimal gastosIniciales = entidadFinanciera.getCostesRegistrales()
                .add(entidadFinanciera.getCostesNotariales())
                .add(entidadFinanciera.getTasacion())
                .add(entidadFinanciera.getComisionActivacion())
                .add(entidadFinanciera.getComisionEstudio());

        BigDecimal descuento = previoVenta.multiply(dto.getPCuotalnicial()).divide(BigDecimal.valueOf(100));
        BigDecimal saldoFinanciar = previoVenta.subtract(descuento);
        BigDecimal montoPrestado = saldoFinanciar.add(gastosIniciales);
        int NCuotasAnio = 360/capitalizacion;
        int NTotalCuotas = NCuotasAnio*dto.getNumeroAnios();

        BigDecimal comisionPeriodica = entidadFinanciera.getComisionPeriodica();
        BigDecimal portes = entidadFinanciera.getPortes();
        BigDecimal gastosAdministracion = entidadFinanciera.getGastosAdministracion();
        BigDecimal seguroDesgravamen = entidadFinanciera.getSeguroDesgravamen();
        BigDecimal seguroRiesgo = entidadFinanciera.getSeguroRiesgo();
        BigDecimal cok = dto.getCok();

        BigDecimal seguroDesgravPer = seguroDesgravamen
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(30), 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(capitalizacion));

        BigDecimal seguroRiesgoPer = seguroRiesgo
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .multiply(previoVenta)
                .divide(BigDecimal.valueOf(NCuotasAnio), 10, RoundingMode.HALF_UP);

        BigDecimal uno = BigDecimal.ONE;
        BigDecimal parte1 = uno.add(cok.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP));
        BigDecimal parte2 = BigDecimal.valueOf(capitalizacion).divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP);
        BigDecimal tasaDescuento = BigDecimal
                .valueOf(Math.pow(parte1.doubleValue(), parte2.doubleValue()))
                .subtract(uno);

        BigDecimal bonoBuenPagador = BigDecimal.ZERO;

        boolean esSostenible = tipo == TipoVivienda.SOSTENIBLE;
        boolean esTradicional = tipo == TipoVivienda.TRADICIONAL;
        boolean esIntegradorSost = tipo == TipoVivienda.INTEGRADOR_SOSTENIBLE;
        boolean esIntegradorTrad = tipo == TipoVivienda.INTEGRADOR_TRADICIONAL;

        if (previoVenta.compareTo(BigDecimal.valueOf(68800)) >= 0 && previoVenta.compareTo(BigDecimal.valueOf(98100)) <= 0) {
            if (esSostenible) bonoBuenPagador = BigDecimal.valueOf(33700);
            else if (esTradicional) bonoBuenPagador = BigDecimal.valueOf(27400);
            else if (esIntegradorSost) bonoBuenPagador = BigDecimal.valueOf(37300);
            else if (esIntegradorTrad) bonoBuenPagador = BigDecimal.valueOf(31000);

        } else if (previoVenta.compareTo(BigDecimal.valueOf(98100)) > 0 && previoVenta.compareTo(BigDecimal.valueOf(146900)) <= 0) {
            if (esSostenible) bonoBuenPagador = BigDecimal.valueOf(29100);
            else if (esTradicional) bonoBuenPagador = BigDecimal.valueOf(22800);
            else if (esIntegradorSost) bonoBuenPagador = BigDecimal.valueOf(32700);
            else if (esIntegradorTrad) bonoBuenPagador = BigDecimal.valueOf(26400);

        } else if (previoVenta.compareTo(BigDecimal.valueOf(146900)) > 0 && previoVenta.compareTo(BigDecimal.valueOf(244600)) <= 0) {
            if (esSostenible) bonoBuenPagador = BigDecimal.valueOf(27200);
            else if (esTradicional) bonoBuenPagador = BigDecimal.valueOf(20900);
            else if (esIntegradorSost) bonoBuenPagador = BigDecimal.valueOf(30800);
            else if (esIntegradorTrad) bonoBuenPagador = BigDecimal.valueOf(24500);

        } else if (previoVenta.compareTo(BigDecimal.valueOf(244600)) > 0 && previoVenta.compareTo(BigDecimal.valueOf(362100)) <= 0) {
            if (esSostenible) bonoBuenPagador = BigDecimal.valueOf(14100);
            else if (esTradicional) bonoBuenPagador = BigDecimal.valueOf(7800);
            else if (esIntegradorSost) bonoBuenPagador = BigDecimal.valueOf(17700);
            else if (esIntegradorTrad) bonoBuenPagador = BigDecimal.valueOf(11400);
        }

        CreditoResponseDTO CreditoResponseDTO = new CreditoResponseDTO();
        CreditoResponseDTO.setTasaInteres(tasaFinal);
        CreditoResponseDTO.setTipoTasaInteres(tipoTasaInteres);
        CreditoResponseDTO.setFrecuenciaPago(frecuenciaPago);
        CreditoResponseDTO.setSaldoFinanciar(saldoFinanciar);
        CreditoResponseDTO.setMontoPrestamo(montoPrestado);
        CreditoResponseDTO.setNCuotasxAnio(NCuotasAnio);
        CreditoResponseDTO.setNTotalCuotas(NTotalCuotas);
        CreditoResponseDTO.setSeguroDegPerd(seguroDesgravPer.multiply(BigDecimal.valueOf(100)));
        CreditoResponseDTO.setSeguroRiesgoPerd(seguroRiesgoPer);
        CreditoResponseDTO.setTasaDescuento(tasaDescuento.multiply(BigDecimal.valueOf(100)));
        CreditoResponseDTO.setBonoBuenPagador(bonoBuenPagador);
        return CreditoResponseDTO;
    }
}