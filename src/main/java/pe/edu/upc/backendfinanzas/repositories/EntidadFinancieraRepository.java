package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backendfinanzas.entities.EntidadFinanciera;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EntidadFinancieraRepository extends JpaRepository<EntidadFinanciera, Integer> {
    Optional<EntidadFinanciera> findByNombreEntidad(String nombreEntidad);
    List<EntidadFinanciera> findByNombreEntidadContainingIgnoreCase(String nombreEntidad);
    List<EntidadFinanciera> findByTeaBetween(BigDecimal minTea, BigDecimal maxTea);
    List<EntidadFinanciera> findByGastosAdministracionLessThan(BigDecimal valor);

    @Query("SELECT e FROM EntidadFinanciera e WHERE e.seguroDesgravamen < :valor")
    List<EntidadFinanciera> buscarPorSeguroDesgravamenMenorA(BigDecimal valor);
}