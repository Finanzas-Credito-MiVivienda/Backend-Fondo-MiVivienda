package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backendfinanzas.entities.Pago;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    //Listar pagos de un crédito específico
    List<Pago> findByCreditoId(Integer creditoId);

    //Buscar pagos entre fechas
    List<Pago> findByFechaPagoBetween(LocalDate fechaInicio, LocalDate fechaFin);

    //Calcular total pagado hasta la fecha
    //@Query("SELECT SUM(p.montoCuota) FROM Pago p WHERE p.credito.id = :idCredito")
    @Query("SELECT COALESCE(SUM(p.montoCuota), 0) FROM Pago p WHERE p.credito.id = :idCredito")
    BigDecimal calcularTotalPagadoPorCredito(@Param("idCredito") int idCredito);
}