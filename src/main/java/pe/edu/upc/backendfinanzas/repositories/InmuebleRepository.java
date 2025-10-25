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
    //Buscar inmuebles por tipo de vivienda (CASA, DEPARTAMENTO, TERRENO)
    List<Inmueble> findByTipo(TipoVivienda tipo);

    //Buscar inmuebles por su estado (pendiente, vendido, reservado)
    //List<Inmueble> findByEstado(EstadoInmueble estado);

    //Buscar un inmueble exacto por su dirección (coincidencia total)
    Optional<Inmueble> findByDireccion(String direccion);

    //Buscar inmuebles cuya dirección contenga una palabra clave (búsqueda parcial, sin importar mayúsculas)
    List<Inmueble> findByDireccionContainingIgnoreCase(String direccion);

    //Buscar inmuebles dentro de un rango de precios (para simulaciones o filtros de crédito)
    List<Inmueble> findByPrecioVentaBetween(BigDecimal precioMin, BigDecimal precioMax);

    //Buscar inmuebles con un área mínima especificada (útil para filtros de tamaño o sostenibilidad)
    List<Inmueble> findByAreaM2GreaterThanEqual(BigDecimal areaMin);

    //Verificar si ya existe un inmueble registrado con la misma dirección (evita duplicados)
    boolean existsByDireccion(String direccion);

    //Buscar viviendas por departamento y provincia.
    List<Inmueble> findByDepartamentoAndProvinciaIgnoreCase(String departamento, String provincia);

    //Buscar viviendas por distrito y rango de precio.
    @Query("SELECT i FROM Inmueble i WHERE LOWER(i.distrito) = LOWER(:distrito) " +
            "AND i.precioVenta BETWEEN :min AND :max")
    List<Inmueble> buscarPorDistritoYRangoPrecio(@Param("distrito") String distrito,
                                                 @Param("min") BigDecimal min,
                                                 @Param("max") BigDecimal max);

    //3️Buscar viviendas con área mínima y tipo de vivienda específico.
    @Query("SELECT i FROM Inmueble i WHERE i.areaM2 >= :areaMin AND i.tipoDeVivienda = :tipo")
    List<Inmueble> buscarPorAreaYTipo(@Param("areaMin") BigDecimal areaMin,
                                      @Param("tipo") TipoVivienda tipo);

    //Buscar las viviendas más costosas (limitadas a top 5)
    @Query("SELECT i FROM Inmueble i ORDER BY i.precioVenta DESC LIMIT 5")
    List<Inmueble> top5MasCaras();

    //Buscar viviendas que superen cierto precio promedio nacional (ejemplo: análisis financiero)
    @Query("SELECT i FROM Inmueble i WHERE i.precioVenta > " +
            "(SELECT AVG(x.precioVenta) FROM Inmueble x)")
    List<Inmueble> viviendasSobrePrecioPromedio();

    //Buscar viviendas con créditos asociados
    @Query("SELECT DISTINCT i FROM Inmueble i JOIN FETCH i.creditos c")
    List<Inmueble> viviendasConCreditos();

    //Buscar viviendas por palabra clave en departamento, provincia o distrito
    @Query("SELECT i FROM Inmueble i WHERE " +
            "LOWER(i.departamento) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.provincia) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.distrito) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Inmueble> buscarPorUbicacionGeneral(@Param("keyword") String keyword);
}