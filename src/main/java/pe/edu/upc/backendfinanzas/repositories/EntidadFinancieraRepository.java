package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backendfinanzas.entities.EntidadFinanciera;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EntidadFinancieraRepository extends JpaRepository<EntidadFinanciera, Integer> {
    // Buscar una entidad financiera por nombre exacto
    Optional<EntidadFinanciera> findByNombreEntidad(String nombreEntidad);

    // Buscar entidades financieras cuyo nombre contenga una palabra
    List<EntidadFinanciera> findByNombreEntidadContainingIgnoreCase(String nombreEntidad);

    // Buscar por rango de TEA
    List<EntidadFinanciera> findByTeaBetween(BigDecimal minTea, BigDecimal maxTea);

    // Buscar entidades con gastos de administración menores a cierto valor
    List<EntidadFinanciera> findByGastosAdministracionLessThan(BigDecimal valor);

    @Query("SELECT e FROM EntidadFinanciera e WHERE e.seguroDesgravamen < :valor")
    List<EntidadFinanciera> buscarPorSeguroDesgravamenMenorA(BigDecimal valor);
}