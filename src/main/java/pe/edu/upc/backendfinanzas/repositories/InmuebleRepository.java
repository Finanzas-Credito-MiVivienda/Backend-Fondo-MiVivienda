package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backendfinanzas.entities.EstadoInmueble;
import pe.edu.upc.backendfinanzas.entities.Inmueble;
import pe.edu.upc.backendfinanzas.entities.TipoInmueble;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InmuebleRepository extends JpaRepository<Inmueble, Integer> {
    List<Inmueble> findByTipo(TipoInmueble tipo);
    List<Inmueble> findByEstado(EstadoInmueble estado);
    Optional<Inmueble> findByDireccion(String direccion);
    List<Inmueble> findByDireccionContainingIgnoreCase(String direccion);
    List<Inmueble> findByPrecioVentaBetween(BigDecimal precioMin, BigDecimal precioMax);
    List<Inmueble> findByAreaM2GreaterThanEqual(BigDecimal areaMin);
    boolean existsByDireccion(String direccion);
}