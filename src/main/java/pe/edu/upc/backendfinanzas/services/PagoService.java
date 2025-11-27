package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.dtos.PlanPagosCronogramaDTO;
import pe.edu.upc.backendfinanzas.dtos.PlanPagosTotalesDTO;
import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.entities.Pago;

import java.util.List;

public interface PagoService {
    List<Pago> findAll();
    Pago insert(Pago pago);
    Pago update(Pago pago);
    Pago findById(int idPago);
    void delete(int idPago);
    Credito obtenerCreditoPorId(int idCredito);

    List<PlanPagosCronogramaDTO> generarCronograma(int idCredito);

    PlanPagosTotalesDTO calcularTotalesCronograma(int idCredito);
}