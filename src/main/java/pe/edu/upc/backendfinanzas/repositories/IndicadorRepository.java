package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backendfinanzas.entities.Indicador;

import java.util.Optional;

public interface IndicadorRepository extends JpaRepository<Indicador, Integer> {
    Optional<Indicador> findByPagoId(Integer pagoId);
    boolean existsByPagoId(Integer pagoId);
}