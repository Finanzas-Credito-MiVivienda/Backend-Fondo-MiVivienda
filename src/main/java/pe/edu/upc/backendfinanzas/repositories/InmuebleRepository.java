package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backendfinanzas.entities.Inmueble;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InmuebleRepository extends JpaRepository<Inmueble, Integer> {
    List<Inmueble> findByTipoVivienda(TipoVivienda tipoVivienda);
    Optional<Inmueble> findByDireccion(String direccion);
    List<Inmueble> findByDireccionContainingIgnoreCase(String direccion);
    List<Inmueble> findByPrecioVentaBetween(BigDecimal precioMin, BigDecimal precioMax);
    List<Inmueble> findByAreaM2GreaterThanEqual(BigDecimal areaMin);
    boolean existsByDireccion(String direccion);
    List<Inmueble> findByDepartamentoAndProvinciaIgnoreCase(String departamento, String provincia);

    @Query("SELECT i FROM Inmueble i WHERE LOWER(i.distrito) = LOWER(:distrito) " +
            "AND i.precioVenta BETWEEN :min AND :max")
    List<Inmueble> buscarPorDistritoYRangoPrecio(@Param("distrito") String distrito,
                                                 @Param("min") BigDecimal min,
                                                 @Param("max") BigDecimal max);

    @Query("SELECT i FROM Inmueble i WHERE i.areaM2 >= :areaMin AND i.estadoVivienda = :tipo")
    List<Inmueble> buscarPorAreaYTipo(@Param("areaMin") BigDecimal areaMin,
                                      @Param("tipo") TipoVivienda tipo);

    @Query("SELECT i FROM Inmueble i ORDER BY i.precioVenta DESC LIMIT 5")
    List<Inmueble> top5MasCaras();

    @Query("SELECT i FROM Inmueble i WHERE i.precioVenta > " +
            "(SELECT AVG(x.precioVenta) FROM Inmueble x)")
    List<Inmueble> viviendasSobrePrecioPromedio();

    @Query("SELECT DISTINCT i FROM Inmueble i JOIN FETCH i.creditos c")
    List<Inmueble> viviendasConCreditos();

    @Query("SELECT i FROM Inmueble i WHERE " +
            "LOWER(i.departamento) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.provincia) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.distrito) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Inmueble> buscarPorUbicacionGeneral(@Param("keyword") String keyword);
}