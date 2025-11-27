package pe.edu.upc.backendfinanzas.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backendfinanzas.dtos.PlanPagosCronogramaDTO;
import pe.edu.upc.backendfinanzas.dtos.PlanPagosTotalesDTO;
import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.entities.Pago;
import pe.edu.upc.backendfinanzas.entities.TipoPeriodoGracia;
import pe.edu.upc.backendfinanzas.repositories.CreditoRepository;
import pe.edu.upc.backendfinanzas.repositories.PagoRepository;
import pe.edu.upc.backendfinanzas.services.PagoService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {
    @Autowired
    PagoRepository pagoRepository;

    @Autowired
    CreditoRepository creditoRepository;

    @Override
    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    @Override
    public Pago insert(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public Pago update(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public Pago findById(int idPago) {
        return pagoRepository.findById(idPago).orElse(new Pago());
    }

    @Override
    public void delete(int idPago) {
        pagoRepository.deleteById(idPago);
    }

    @Override
    public Credito obtenerCreditoPorId(int idCredito) {
        return creditoRepository.findById(idCredito).orElse(null);
    }

    @Override
    public List<PlanPagosCronogramaDTO> generarCronograma(int idCredito) {
        Credito credito = creditoRepository.findById(idCredito).orElse(null);
        if (credito == null) return new ArrayList<>();

        BigDecimal saldoFinalAnterior = BigDecimal.ZERO;
        BigDecimal tem = credito.getTasaInteres();
        int numeroTotalCuotas = credito.getNTotalCuotas();


        TipoPeriodoGracia tipoPeriodoGracia = credito.getTipoPeriodoGracia();
        int periodoGracia = credito.getPeriodoGracia();

        List<PlanPagosCronogramaDTO> pagos = new ArrayList<>();

        for (int i = 1; i <= numeroTotalCuotas; i++) {

            PlanPagosCronogramaDTO plan = new PlanPagosCronogramaDTO();

            plan.setNumeroCuota(i);
            plan.setTem(tem);
            plan.setIa(BigDecimal.ZERO);
            plan.setIp(BigDecimal.ZERO);

            String tipoPeriodoStr;

            if (tipoPeriodoGracia == TipoPeriodoGracia.NINGUNO) {
                tipoPeriodoStr = "S";
            } else {
                if (i <= periodoGracia && i != 0) {
                    if (tipoPeriodoGracia == TipoPeriodoGracia.GRACIA_TOTAL) {
                        tipoPeriodoStr = "T";
                    } else {
                        tipoPeriodoStr = "P";
                    }
                } else {
                    tipoPeriodoStr = "S";
                }
            }

            plan.setTipoPeriodo(tipoPeriodoStr);

            BigDecimal saldoInicial;

            if (i == 0) {
                saldoInicial = BigDecimal.ZERO;
            } else if (i == 1) {
                saldoInicial = credito.getMontoPrestamo();
            } else {
                saldoInicial = saldoFinalAnterior;
            }

            plan.setSaldoInicial(saldoInicial);

            BigDecimal interes = saldoInicial.multiply(tem.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)).negate()
                    .setScale(7, BigDecimal.ROUND_HALF_UP);

            plan.setInteres(interes);

            BigDecimal cuota;
            if (i == 0) {
                cuota = BigDecimal.ZERO;
            } else if (tipoPeriodoStr.equals("T")) {
                cuota = BigDecimal.ZERO;
            } else if (tipoPeriodoStr.equals("P")) {
                cuota = interes;
            } else {
                int cuotasRestantes = numeroTotalCuotas - i + 1;
                BigDecimal temDecimal = tem.divide(new BigDecimal("100"), 7, RoundingMode.HALF_UP);
                if (tem.compareTo(BigDecimal.ZERO) == 0) {
                    cuota = saldoInicial.divide(new BigDecimal(cuotasRestantes), 7, RoundingMode.HALF_UP);
                } else {
                    BigDecimal unoMasI = BigDecimal.ONE.add(temDecimal);
                    BigDecimal potenciaPositiva = unoMasI.pow(cuotasRestantes);
                    BigDecimal potenciaNegativa = BigDecimal.ONE.divide(potenciaPositiva, 7, RoundingMode.HALF_UP);

                    BigDecimal denominador = BigDecimal.ONE.subtract(potenciaNegativa);

                    cuota = saldoInicial.multiply(temDecimal).divide(denominador, 7, RoundingMode.HALF_UP);
                }
            }

            plan.setCuota(cuota);

            BigDecimal amortizacion;

            if (tipoPeriodoStr.equals("T") || tipoPeriodoStr.equals("P")) {
                amortizacion = BigDecimal.ZERO;
            } else {
                amortizacion = cuota.add(interes).setScale(7, RoundingMode.HALF_UP).negate();
            }

            plan.setAmortizacion(amortizacion);

            BigDecimal segDes = BigDecimal.ZERO;

            if (i <= numeroTotalCuotas) {
                segDes = saldoInicial.multiply(credito.getSeguroDegPerd().divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP))
                        .negate().setScale(7, RoundingMode.HALF_UP);
            }

            plan.setSeguroDesgravamen(segDes);

            BigDecimal segRies = BigDecimal.ZERO;

            if (i <= numeroTotalCuotas) {
                segRies = credito.getSeguroRiesgoPerd().divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)
                        .multiply(credito.getInmueble().getPrecioVenta())
                        .negate().setScale(0, RoundingMode.HALF_UP);
            }

            plan.setSeguroRiesgo(segRies);

            BigDecimal comPerd = credito.getEntidadFinanciera().getComisionPeriodica();
            BigDecimal comision = BigDecimal.ZERO;

            if (i <= numeroTotalCuotas) {
                comision = comPerd.negate();
            }

            plan.setComision(comision);

            BigDecimal portes = BigDecimal.ZERO;

            if (i <= numeroTotalCuotas) {
                portes = credito.getEntidadFinanciera().getPortes().negate().setScale(7, RoundingMode.HALF_UP);
            }

            plan.setPortes(portes);

            BigDecimal gastosAdm = BigDecimal.ZERO;

            if (i <= numeroTotalCuotas) {
                gastosAdm = credito.getEntidadFinanciera().getGastosAdministracion().negate().setScale(0, RoundingMode.HALF_UP);
            }

            plan.setGastosAdministracion(gastosAdm);

            BigDecimal saldoFinal = BigDecimal.ZERO;

            if (i <= numeroTotalCuotas) {
                if (tipoPeriodoStr.equals("T")) {
                    saldoFinal = saldoInicial.subtract(interes);
                } else {
                    saldoFinal = saldoInicial.add(amortizacion);
                }
            }

            plan.setSaldoFinal(saldoFinal);

            BigDecimal flujo = BigDecimal.ZERO;
            BigDecimal segDesAplicado =
                    (tipoPeriodoStr.equals("P") || tipoPeriodoStr.equals("T"))
                            ? segDes
                            : BigDecimal.ZERO;

            if (i <= numeroTotalCuotas) {
                flujo = cuota.negate()
                        .add(segRies)
                        .add(comision)
                        .add(portes)
                        .add(gastosAdm)
                        .add(segDesAplicado)
                        .setScale(7, RoundingMode.HALF_UP);
            }

            plan.setFlujo(flujo);

            System.out.println(
                    "Cuota " + i
                            + " - TEM: " + plan.getTem().setScale(2, RoundingMode.HALF_UP)
                            + " - IA: " + plan.getIa().setScale(2, RoundingMode.HALF_UP)
                            + " - IP: " + plan.getIp().setScale(2, RoundingMode.HALF_UP)
                            + " - TipoPeriodo: " + plan.getTipoPeriodo()
                            + " - saldoInicial: " + saldoInicial.setScale(2, RoundingMode.HALF_UP)
                            + " - interes: " + interes.setScale(2, RoundingMode.HALF_UP)
                            + " - cuota: " + cuota.setScale(2, RoundingMode.HALF_UP)
                            + " - amortizacion: " + amortizacion.setScale(2, RoundingMode.HALF_UP)
                            + " - segDesgravamen: " + segDes.setScale(2, RoundingMode.HALF_UP)
                            + " - segRiesgo: " + segRies
                            + " - comision: " + comision.setScale(2, RoundingMode.HALF_UP)
                            + " - portes: " + portes.setScale(2, RoundingMode.HALF_UP)
                            + " - gastosAdm: " + gastosAdm.setScale(2, RoundingMode.HALF_UP)
                            + " - saldoFinal: " + saldoFinal.setScale(2, RoundingMode.HALF_UP)
                            + " - flujo: " + flujo.setScale(2, RoundingMode.HALF_UP)
            );

            PlanPagosCronogramaDTO pagosCronogramaDTO = new PlanPagosCronogramaDTO();
            pagosCronogramaDTO.setNumeroCuota(i);
            pagosCronogramaDTO.setTem(plan.getTem());
            pagosCronogramaDTO.setIa(plan.getIa());
            pagosCronogramaDTO.setIp(plan.getIp());
            pagosCronogramaDTO.setTipoPeriodo(plan.getTipoPeriodo());
            pagosCronogramaDTO.setSaldoInicial(saldoInicial);
            pagosCronogramaDTO.setInteres(interes);
            pagosCronogramaDTO.setCuota(cuota);
            pagosCronogramaDTO.setAmortizacion(amortizacion);
            pagosCronogramaDTO.setSeguroDesgravamen(segDes);
            pagosCronogramaDTO.setSeguroRiesgo(segRies);
            pagosCronogramaDTO.setComision(comision);
            pagosCronogramaDTO.setPortes(portes);
            pagosCronogramaDTO.setGastosAdministracion(gastosAdm);
            pagosCronogramaDTO.setSaldoFinal(saldoFinal);
            pagosCronogramaDTO.setFlujo(flujo);

            pagos.add(pagosCronogramaDTO);
            saldoFinalAnterior = saldoFinal.setScale(7, RoundingMode.HALF_UP);
        }
        return pagos;
    }

    @Override
    public PlanPagosTotalesDTO calcularTotalesCronograma(int idCredito) {
        List<PlanPagosCronogramaDTO> cronograma = generarCronograma(idCredito);

        if (cronograma == null || cronograma.isEmpty()) {
            return new PlanPagosTotalesDTO(
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
            );
        }

        BigDecimal totalIntereses = BigDecimal.ZERO;
        BigDecimal totalAmortizacion = BigDecimal.ZERO;
        BigDecimal totalSegDes = BigDecimal.ZERO;
        BigDecimal totalSegRiesgo = BigDecimal.ZERO;
        BigDecimal totalComisionPeriodica = BigDecimal.ZERO;
        BigDecimal totalPortesGastos = BigDecimal.ZERO;

        for (PlanPagosCronogramaDTO cronogramaDTO : cronograma) {
            BigDecimal interesCalculado = cronogramaDTO.getCuota()
                    .add(cronogramaDTO.getAmortizacion())
                    .add(cronogramaDTO.getSeguroDesgravamen());
            totalIntereses = totalIntereses.add(interesCalculado);

            totalAmortizacion = totalAmortizacion.add(cronogramaDTO.getAmortizacion().negate());

            totalSegDes = totalSegDes.add(cronogramaDTO.getSeguroDesgravamen().negate());

            totalSegRiesgo = totalSegRiesgo.add(cronogramaDTO.getSeguroRiesgo().negate());

            totalComisionPeriodica = totalComisionPeriodica.add(cronogramaDTO.getComision().negate());

            BigDecimal portesGastosAdm = cronogramaDTO.getPortes().add(cronogramaDTO.getGastosAdministracion()).negate();
            totalPortesGastos = totalPortesGastos.add(portesGastosAdm);
        }

        PlanPagosTotalesDTO pagosTotalesDTO = new PlanPagosTotalesDTO();
        pagosTotalesDTO.setTotalIntereses(totalIntereses);
        pagosTotalesDTO.setTotalAmortizacionCapital(totalAmortizacion);
        pagosTotalesDTO.setTotalSeguroDesgravamen(totalSegDes);
        pagosTotalesDTO.setTotalSeguroRiesgo(totalSegRiesgo);
        pagosTotalesDTO.setTotalComisionPeriodica(totalComisionPeriodica);
        pagosTotalesDTO.setTotalPortesGastosAdm(totalPortesGastos);

        System.out.println("- Interes: " + totalIntereses.setScale(2, RoundingMode.HALF_UP));
        System.out.println("- Amortizacion: " + totalAmortizacion.setScale(2, RoundingMode.HALF_UP));
        System.out.println("- Seguro Desgravamen: " + totalSegDes.setScale(2, RoundingMode.HALF_UP));
        System.out.println("- Seguro Riesgo: " + totalSegRiesgo.setScale(0, RoundingMode.HALF_UP));
        System.out.println("- Comision Periodica: " + totalComisionPeriodica.setScale(2, RoundingMode.HALF_UP));
        System.out.println("- Portes/GastosAdm: " + totalPortesGastos.setScale(0, RoundingMode.HALF_UP));

        return pagosTotalesDTO;
    }
}