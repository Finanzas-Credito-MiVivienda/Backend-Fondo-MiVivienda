package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.entities.Inmueble;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InmuebleService {
    List<Inmueble> list();
    void insert(Inmueble ofertaInmueble);
    void update(Inmueble ofertaInmueble);
    Inmueble listId(int idInmueble);
    void delete(int idInmueble);

    // Buscar inmuebles por tipo de vivienda
    List<Inmueble> listByTipo(TipoVivienda tipo);

    // Buscar inmueble exacto por dirección
    Optional<Inmueble> findByDireccion(String direccion);

    // Buscar inmuebles por palabra clave en dirección
    List<Inmueble> listByDireccionParcial(String direccion);

    // Buscar inmuebles por rango de precio
    List<Inmueble> listByPrecioBetween(BigDecimal min, BigDecimal max);

    // Buscar inmuebles por área mínima
    List<Inmueble> listByAreaMinima(BigDecimal areaMin);

    // Verificar si existe un inmueble por dirección
    boolean existsByDireccion(String direccion);

    // Buscar viviendas por departamento y provincia
    List<Inmueble> listByDepartamentoAndProvincia(String departamento, String provincia);

    // Buscar viviendas por distrito y rango de precio
    List<Inmueble> listByDistritoYRangoPrecio(String distrito, BigDecimal min, BigDecimal max);

    // Buscar viviendas por área mínima y tipo de vivienda
    List<Inmueble> listByAreaAndTipo(BigDecimal areaMin, TipoVivienda tipo);

    // Listar top 5 viviendas más costosas
    List<Inmueble> listTop5MasCaras();

    // Listar viviendas con precio superior al promedio general
    List<Inmueble> listSobrePrecioPromedio();

    // Listar viviendas que tienen créditos asociados
    List<Inmueble> listConCreditos();

    // Buscar viviendas por coincidencia en departamento, provincia o distrito
    List<Inmueble> listByUbicacionGeneral(String keyword);
}