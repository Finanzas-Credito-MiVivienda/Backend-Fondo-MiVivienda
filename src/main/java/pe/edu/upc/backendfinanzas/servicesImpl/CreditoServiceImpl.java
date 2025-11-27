package pe.edu.upc.backendfinanzas.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backendfinanzas.dtos.request.CreditoRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.request.UserRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.CreditoResponseDTO;
import pe.edu.upc.backendfinanzas.entities.*;
import pe.edu.upc.backendfinanzas.repositories.CreditoRepository;
import pe.edu.upc.backendfinanzas.services.CreditoService;
import pe.edu.upc.backendfinanzas.services.EntidadFinancieraService;
import pe.edu.upc.backendfinanzas.services.InmuebleService;

import java.math.MathContext;
import java.math.RoundingMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CreditoServiceImpl implements CreditoService {
    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private EntidadFinancieraService entidadFinancieraService;

    @Autowired
    private InmuebleService inmuebleService;

    @Override
    public List<Credito> findAll() {
        return creditoRepository.findAll();
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
    public CreditoResponseDTO calcularCredito(CreditoRequestDTO dto) {
        int idEntidadFinanciera = dto.getIdEntidadFinanciera();
        int idInmueble = dto.getIdInmueble();

        TipoTasaInteres tipoTasaInteres = dto.getTipoTasaInteres();
        FrecuenciaPago frecuenciaPago = dto.getFrecuenciaPago();

        EntidadFinanciera entidadFinanciera = entidadFinancieraService.listId(idEntidadFinanciera);
        Inmueble inmueble = inmuebleService.listId(idInmueble);
        TipoVivienda tipo = inmueble.getTipoVivienda();

        BigDecimal nuevaTea = dto.getTasaInteres();

        BigDecimal tasaFinal = BigDecimal.ZERO;
        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);

        int capitalizacion = 30;
        if (tipoTasaInteres == TipoTasaInteres.NOMINAL) {
            if (frecuenciaPago != null) {
                switch (frecuenciaPago) {
                    case MENSUAL -> capitalizacion = 30;
                    case BIMESTRAL -> capitalizacion = 60;
                    case TRIMESTRAL -> capitalizacion = 90;
                    case SEMESTRAL -> capitalizacion = 180;
                    default -> capitalizacion = 360;
                }
            }

            BigDecimal m = BigDecimal.valueOf(360.0 / capitalizacion);
            BigDecimal n = BigDecimal.valueOf(30.0 / capitalizacion);

            BigDecimal parte1 = nuevaTea.divide(BigDecimal.valueOf(100), mc).divide(m, mc);
            BigDecimal parte2 = BigDecimal.ONE.add(parte1);

            double pow = Math.pow(parte2.doubleValue(), n.doubleValue());
            tasaFinal = BigDecimal.valueOf(pow - 1).multiply(BigDecimal.valueOf(100));
        } else {

            BigDecimal diasTEM = BigDecimal.valueOf(30);
            BigDecimal diasTEA = BigDecimal.valueOf(360);

            BigDecimal parte1 = BigDecimal.ONE.add(nuevaTea.divide(BigDecimal.valueOf(100), mc));
            BigDecimal parte2 = diasTEM.divide(diasTEA, mc);

            double pow = Math.pow(parte1.doubleValue(), parte2.doubleValue());
            tasaFinal = BigDecimal.valueOf(pow - 1).multiply(BigDecimal.valueOf(100));
        }

        BigDecimal previoVenta = inmueble.getPrecioVenta();
        BigDecimal gastosIniciales = entidadFinanciera.getCostesRegistrales()
                .add(entidadFinanciera.getCostesNotariales())
                .add(entidadFinanciera.getTasacion())
                .add(entidadFinanciera.getComisionActivacion())
                .add(entidadFinanciera.getComisionEstudio());

        BigDecimal descuento = previoVenta.multiply(dto.getPCuotaInicial()).divide(BigDecimal.valueOf(100));
        BigDecimal saldoFinanciar = previoVenta.subtract(descuento);
        BigDecimal montoPrestado = saldoFinanciar.add(gastosIniciales);

        int NCuotasAnio = 360 / capitalizacion;
        int NTotalCuotas = NCuotasAnio * dto.getNumeroAnios();

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
                .multiply(BigDecimal.valueOf(capitalizacion))
                .divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP);

        BigDecimal parte1 = BigDecimal.ONE.add(cok.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP));
        BigDecimal parte2 = BigDecimal.valueOf(capitalizacion).divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP);
        BigDecimal tasaDescuento = BigDecimal
                .valueOf(Math.pow(parte1.doubleValue(), parte2.doubleValue()))
                .subtract(BigDecimal.ONE);

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

        BigDecimal saldoFinanciar2 = previoVenta.subtract(descuento).subtract(bonoBuenPagador);
        BigDecimal montoPrestado2 = saldoFinanciar2.add(gastosIniciales);

        TipoPeriodoGracia tipoPeriodoGracia = dto.getTipoPeriodoGracia();
        int periodoGracia = dto.getPeriodoGracia();

        if (tipoPeriodoGracia == TipoPeriodoGracia.GRACIA_TOTAL || tipoPeriodoGracia == TipoPeriodoGracia.GRACIA_PARCIAL) {
            if (periodoGracia < 1 || periodoGracia > 6) {
                throw new IllegalArgumentException("El Periodo de Gracia debe ser entre 1 y 6");
            }
        } else if (tipoPeriodoGracia == TipoPeriodoGracia.NINGUNO) {
            periodoGracia = 0;
        }

        CreditoResponseDTO response = new CreditoResponseDTO();

        response.setTasaInteres(tasaFinal);
        response.setTipoTasaInteres(tipoTasaInteres);
        response.setFrecuenciaPago(frecuenciaPago);
        response.setSaldoFinanciar(saldoFinanciar2);
        response.setMontoPrestamo(montoPrestado2);
        response.setNCuotasxAnio(NCuotasAnio);
        response.setNTotalCuotas(NTotalCuotas);
        response.setSeguroDegPerd(seguroDesgravPer.multiply(BigDecimal.valueOf(100)));
        response.setSeguroRiesgoPerd(seguroRiesgoPer.multiply(BigDecimal.valueOf(100)));
        response.setTasaDescuento(tasaDescuento.multiply(BigDecimal.valueOf(100)));
        response.setBonoBuenPagador(bonoBuenPagador);

        response.setFechaInicio(LocalDate.now());
        response.setTipoTasaInteres(tipoTasaInteres);
        response.setPeriodoGracia(periodoGracia);
        response.setTipoPeriodoGracia(tipoPeriodoGracia);
        response.setNumeroAnios(dto.getNumeroAnios());
        response.setNumeroDiasxAnio(360);
        response.setPCuotaInicial(dto.getPCuotaInicial());

        response.setIdUsuario(dto.getIdUsuario());
        response.setIdEntidadFinanciera(dto.getIdEntidadFinanciera());
        response.setIdInmueble(dto.getIdInmueble());

        response.setTeaOriginal(dto.getTasaInteres());

        return response;
    }

    @Override
    public CreditoResponseDTO registrarCredito(CreditoRequestDTO dto) {

        CreditoResponseDTO response = this.calcularCredito(dto);

        Credito credito = new Credito();

        credito.setMontoPrestamo(response.getMontoPrestamo());
        credito.setSaldoFinanciar(response.getSaldoFinanciar());
        credito.setTipoTasaInteres(response.getTipoTasaInteres());
        credito.setTasaInteres(response.getTasaInteres());
        credito.setFrecuenciaPago(response.getFrecuenciaPago());
        credito.setFechaInicio(LocalDate.now());
        credito.setBonoBuenPagador(response.getBonoBuenPagador());
        credito.setTipoPeriodoGracia(response.getTipoPeriodoGracia());
        credito.setPeriodoGracia(response.getPeriodoGracia());
        credito.setPCuotaInicial(response.getPCuotaInicial());
        credito.setNumeroAnios(response.getNumeroAnios());
        credito.setNumeroDiasxAnio(response.getNumeroDiasxAnio());
        credito.setNCuotasxAnio(response.getNCuotasxAnio());
        credito.setNTotalCuotas(response.getNTotalCuotas());
        credito.setSeguroDegPerd(response.getSeguroDegPerd());
        credito.setSeguroRiesgoPerd(response.getSeguroRiesgoPerd());
        credito.setCok(dto.getCok());

        // usuario
        Users usuario = new Users();
        usuario.setId(dto.getIdUsuario());
        credito.setUsuario(usuario);

        // inmueble
        Inmueble inmueble = inmuebleService.listId(dto.getIdInmueble());
        credito.setInmueble(inmueble);

        // entidad financiera
        EntidadFinanciera ef = entidadFinancieraService.listId(dto.getIdEntidadFinanciera());
        ef.setCredito(credito);
        credito.setEntidadFinanciera(ef);

        Credito guardado = creditoRepository.save(credito);

        CreditoResponseDTO finalResponse = new CreditoResponseDTO();

        finalResponse.setId(guardado.getId());
        finalResponse.setMontoPrestamo(guardado.getMontoPrestamo());
        finalResponse.setSaldoFinanciar(guardado.getSaldoFinanciar());
        finalResponse.setTipoTasaInteres(guardado.getTipoTasaInteres());
        finalResponse.setTasaInteres(guardado.getTasaInteres());
        finalResponse.setFrecuenciaPago(guardado.getFrecuenciaPago());
        finalResponse.setFechaInicio(guardado.getFechaInicio());
        finalResponse.setBonoBuenPagador(guardado.getBonoBuenPagador());
        finalResponse.setTipoPeriodoGracia(guardado.getTipoPeriodoGracia());
        finalResponse.setPeriodoGracia(guardado.getPeriodoGracia());
        finalResponse.setPCuotaInicial(guardado.getPCuotaInicial());
        finalResponse.setNumeroAnios(guardado.getNumeroAnios());
        finalResponse.setNumeroDiasxAnio(guardado.getNumeroDiasxAnio());
        finalResponse.setNCuotasxAnio(guardado.getNCuotasxAnio());
        finalResponse.setNTotalCuotas(guardado.getNTotalCuotas());
        finalResponse.setSeguroDegPerd(guardado.getSeguroDegPerd());
        finalResponse.setSeguroRiesgoPerd(guardado.getSeguroRiesgoPerd());
        finalResponse.setTasaDescuento(response.getTasaDescuento());

        finalResponse.setIdUsuario(dto.getIdUsuario());
        finalResponse.setIdEntidadFinanciera(dto.getIdEntidadFinanciera());
        finalResponse.setIdInmueble(dto.getIdInmueble());
        finalResponse.setTeaOriginal(dto.getTasaInteres());

        return finalResponse;
    }
}