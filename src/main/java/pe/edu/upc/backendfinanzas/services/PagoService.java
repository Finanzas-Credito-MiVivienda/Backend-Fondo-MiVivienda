package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.entities.Pago;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PagoService {
    List<Pago> findAll();
    Pago insert(Pago pago);
    Pago update(Pago pago);
    Pago findById(int idPago);
    void delete(int idPago);

    //Generar los pagos de un crédito (flujo mensual)
    List<Pago> generarPagosPorCredito(int idCredito);

    //Listar todos los pagos de un crédito
    List<Pago> findByCredito(int idCredito);

    //Buscar pagos entre dos fechas dadas
    List<Pago> findByFechaBetween(LocalDate inicio, LocalDate fin);
}