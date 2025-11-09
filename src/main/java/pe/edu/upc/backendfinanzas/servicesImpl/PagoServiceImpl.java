package pe.edu.upc.backendfinanzas.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.entities.Pago;
import pe.edu.upc.backendfinanzas.repositories.CreditoRepository;
import pe.edu.upc.backendfinanzas.repositories.PagoRepository;
import pe.edu.upc.backendfinanzas.services.PagoService;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public List<Pago> generarPagosPorCredito(int idCredito) {
        Credito credito = creditoRepository.findById(idCredito).orElse(null);
        if (credito == null) {
            return new ArrayList<>();
        }

        // Convertir BigDecimal a double para los cálculos financieros
        double monto = credito.getMontoPrestamo().doubleValue();
        double tasaAnual = credito.getTasaInteres().doubleValue();
        int meses = 30;
        LocalDate fechaInicio = credito.getFechaInicio();

        // Convertir tasa anual a tasa mensual (sistema francés)
        double tasaMensual = tasaAnual / 12 / 100;

        // Fórmula de cuota fija (sistema francés)
        double cuota = (monto * tasaMensual) / (1 - Math.pow(1 + tasaMensual, -meses));

        List<Pago> pagos = new ArrayList<>();
        double saldo = monto;

        for (int i = 1; i <= meses; i++) {
            double interes = saldo * tasaMensual;
            double amortizacion = cuota - interes;
            double saldoFinal = saldo - amortizacion;
            //saldo -= amortizacion;

            Pago pago = new Pago();
            pago.setNumeroCuota(i);
            pago.setFechaPago(fechaInicio.plusMonths(i));
            pago.setSaldoInicial(BigDecimal.valueOf(saldo));
            pago.setInteres(BigDecimal.valueOf(interes));
            pago.setAmortizacion(BigDecimal.valueOf(amortizacion));
            pago.setMontoCuota(BigDecimal.valueOf(cuota));
            pago.setSaldoFinal(BigDecimal.valueOf(saldoFinal));
            pago.setTipoPeriodoGracia(credito.getTipoPeriodoGracia());
            pago.setCredito(credito);
            /*
            pago.setNumeroCuota(i);
            pago.setMontoCuota(BigDecimal.valueOf(cuota));
            pago.setInteres(BigDecimal.valueOf(interes));
            pago.setAmortizacion(BigDecimal.valueOf(amortizacion));
            pago.setFechaPago(fechaInicio.plusMonths(i));
            pago.setCredito(credito);
            */
            pagoRepository.save(pago);
            pagos.add(pago);
        }
        return pagos;
    }

    @Override
    public List<Pago> findByCredito(int idCredito) {
        return pagoRepository.findByCreditoId(idCredito);
    }

    @Override
    public List<Pago> findByFechaBetween(LocalDate inicio, LocalDate fin) {
        return pagoRepository.findByFechaPagoBetween(inicio, fin);
    }
}