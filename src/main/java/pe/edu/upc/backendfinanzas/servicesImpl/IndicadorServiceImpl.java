package pe.edu.upc.backendfinanzas.servicesImpl;

import org.springframework.stereotype.Service;
import pe.edu.upc.backendfinanzas.dtos.IndicadorDTO;
import pe.edu.upc.backendfinanzas.dtos.PlanPagosCronogramaDTO;
import pe.edu.upc.backendfinanzas.entities.*;
import pe.edu.upc.backendfinanzas.services.IndicadorService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndicadorServiceImpl implements IndicadorService {
    @Override
    public IndicadorDTO calcularIndicadorPorCronograma(List<PlanPagosCronogramaDTO> cronograma, Credito credito) {
        if (cronograma == null || cronograma.isEmpty()) {
            return new IndicadorDTO();
        }

        BigDecimal cok = credito.getCok();
        FrecuenciaPago frecuenciaPago = credito.getFrecuenciaPago();

        int capitalizacion = 30;
        if (credito.getTipoTasaInteres() == TipoTasaInteres.NOMINAL) {
            if (frecuenciaPago != null) {
                switch (frecuenciaPago) {
                    case MENSUAL -> capitalizacion = 30;
                    case BIMESTRAL -> capitalizacion = 60;
                    case TRIMESTRAL -> capitalizacion = 90;
                    case SEMESTRAL -> capitalizacion = 180;
                    default -> capitalizacion = 360;
                }
            }
        }

        IndicadorDTO indicadorDTO = new IndicadorDTO();

        BigDecimal parte1 = BigDecimal.ONE.add(cok.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP));
        BigDecimal parte2 = BigDecimal.valueOf(capitalizacion).divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP);
        BigDecimal tasaDescuento = BigDecimal.valueOf(Math.pow(parte1.doubleValue(), parte2.doubleValue()))
                .subtract(BigDecimal.ONE);
        tasaDescuento = tasaDescuento.multiply(BigDecimal.valueOf(100));

        indicadorDTO.setTasaDescuento(tasaDescuento);

        BigDecimal guess = new BigDecimal("0.01");
        BigDecimal tol = new BigDecimal("0.0000001");
        int maxIter = 200;
        BigDecimal irr = guess;

        List<BigDecimal> flujos = new ArrayList<>();
        flujos.add(credito.getMontoPrestamo());

        for (var fila : cronograma) {
            BigDecimal flujo = fila.getFlujo() != null ? fila.getFlujo() : BigDecimal.ZERO;
            flujos.add(flujo);
        }

        for (int i = 0; i < maxIter; i++) {
            BigDecimal van = BigDecimal.ZERO;
            BigDecimal dVan = BigDecimal.ZERO;

            for (int t = 0; t < flujos.size(); t++) {

                BigDecimal flujo = flujos.get(t);
                BigDecimal unoMasIrr = BigDecimal.ONE.add(irr);
                BigDecimal pow = unoMasIrr.pow(t, MathContext.DECIMAL128);

                van = van.add(flujo.divide(pow, 20, RoundingMode.HALF_UP));

                if (t > 0) {
                    BigDecimal derivada = flujo
                            .multiply(BigDecimal.valueOf(-t))
                            .divide(pow.multiply(unoMasIrr), 20, RoundingMode.HALF_UP);
                    dVan = dVan.add(derivada);
                }
            }

            BigDecimal nuevoIrr = irr.subtract(van.divide(dVan, 20, RoundingMode.HALF_UP));

            if (nuevoIrr.subtract(irr).abs().compareTo(tol) < 0) {
                irr = nuevoIrr;
                break;
            }

            irr = nuevoIrr;
        }

        BigDecimal tirPeriodo = irr.multiply(BigDecimal.valueOf(100));
        indicadorDTO.setTirPeriodo(tirPeriodo);

        BigDecimal tirDecimal = tirPeriodo.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal exponente = BigDecimal.valueOf((360.0) / capitalizacion);
        BigDecimal tceaOperacion = BigDecimal.valueOf(Math.pow(1 + tirDecimal.doubleValue(), exponente.doubleValue()))
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(5, RoundingMode.HALF_UP);

        indicadorDTO.setTceaOperacion(tceaOperacion);

        BigDecimal van = credito.getMontoPrestamo();

        BigDecimal tasaDescDecimal = tasaDescuento
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        for (int t = 0; t < cronograma.size(); t++) {

            BigDecimal flujo = cronograma.get(t).getFlujo();
            if (flujo == null) flujo = BigDecimal.ZERO;

            BigDecimal flujoDescontado =
                    flujo.divide(
                            BigDecimal.ONE.add(tasaDescDecimal).pow(t + 1),
                            10,
                            RoundingMode.HALF_UP
                    );

            van = van.add(flujoDescontado);
        }

        indicadorDTO.setVan(van.setScale(2, RoundingMode.HALF_UP));

        System.out.println("- COK: " + tasaDescuento.setScale(5, RoundingMode.HALF_UP));
        System.out.println("- TIR: " + tirPeriodo.setScale(5, RoundingMode.HALF_UP));
        System.out.println("- TCEA: " + tceaOperacion.setScale(5, RoundingMode.HALF_UP));
        System.out.println("- VAN: " + van.setScale(2, RoundingMode.HALF_UP));

        return indicadorDTO;
    }
}