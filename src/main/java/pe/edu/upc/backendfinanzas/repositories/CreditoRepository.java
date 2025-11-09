package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.entities.Moneda;
import pe.edu.upc.backendfinanzas.entities.TipoPeriodoGracia;
import pe.edu.upc.backendfinanzas.entities.TipoTasaInteres;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CreditoRepository extends JpaRepository<Credito, Integer> {
    // Buscar créditos por usuario
    List<Credito> findByUsuarioId(int usuarioId);

    // Buscar por rango de monto prestado
    List<Credito> findByMontoPrestamoBetween(BigDecimal min, BigDecimal max);

    // Buscar por fecha de inicio después de una fecha dada
    List<Credito> findByFechaInicioAfter(LocalDate fechaInicio);

    // Buscar por tipo de tasa de interés
    List<Credito> findByTipoTasaInteres(TipoTasaInteres tipo);

    // Buscar créditos con tasa de interés mayor a un valor
    @Query("SELECT c FROM Credito c WHERE c.tasaInteres > :tasa")
    List<Credito> findByTasaInteresMayorA(@Param("tasa") BigDecimal tasa);

    // Buscar créditos según tipo de periodo de gracia
    List<Credito> findByTipoPeriodoGracia(TipoPeriodoGracia tipo);

    // Buscar por departamento o provincia del inmueble
    @Query("SELECT c FROM Credito c WHERE c.inmueble.departamento = :departamento OR c.inmueble.provincia = :provincia")
    List<Credito> findByDepartamentoOProvincia(@Param("departamento") String departamento,
                                               @Param("provincia") String provincia);

    // Créditos con monto mayor al promedio general
    @Query("SELECT c FROM Credito c WHERE c.montoPrestamo > (SELECT AVG(c2.montoPrestamo) FROM Credito c2)")
    List<Credito> findCreditosSobrePromedio();
}