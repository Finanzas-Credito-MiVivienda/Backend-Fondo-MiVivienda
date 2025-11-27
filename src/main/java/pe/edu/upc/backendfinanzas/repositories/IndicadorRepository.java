package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backendfinanzas.entities.Indicador;

public interface IndicadorRepository extends JpaRepository<Indicador, Integer> {

}