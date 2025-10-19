package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backendfinanzas.entities.Pago;

import java.time.LocalDate;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByCreditoId(Integer creditoId); //idCredito
    List<Pago> findByFechaPagoBetween(LocalDate fechaInicio, LocalDate fechaFin);
}