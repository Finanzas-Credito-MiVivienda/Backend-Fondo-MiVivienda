package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.entities.Inmueble;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InmuebleService {
    public List<Inmueble> list();

    public void insert(Inmueble inmueble);

    public void update(Inmueble inmueble);

    public Inmueble listId(int idInmueble);

    public void delete(int idInmueble);

    // Buscar inmuebles por tipo de vivienda (CASA, DEPARTAMENTO, TERRENO)
    public List<Inmueble> listByTipo(TipoVivienda tipo);

    // Buscar inmueble exacto por dirección
    public Optional<Inmueble> findByDireccion(String direccion);

    // Buscar inmuebles por palabra clave en dirección
    public List<Inmueble> listByDireccionParcial(String direccion);

    // Buscar inmuebles por rango de precio
    public List<Inmueble> listByPrecioBetween(BigDecimal min, BigDecimal max);

    // Buscar inmuebles por área mínima
    public List<Inmueble> listByAreaMinima(BigDecimal areaMin);

    // Verificar si existe un inmueble por dirección
    public boolean existsByDireccion(String direccion);

    // Buscar viviendas por departamento y provincia
    public List<Inmueble> listByDepartamentoAndProvincia(String departamento, String provincia);

    // Buscar viviendas por distrito y rango de precio
    public List<Inmueble> listByDistritoYRangoPrecio(String distrito, BigDecimal min, BigDecimal max);

    // Buscar viviendas por área mínima y tipo de vivienda
    public List<Inmueble> listByAreaAndTipo(BigDecimal areaMin, TipoVivienda tipo);

    // Listar top 5 viviendas más costosas
    public List<Inmueble> listTop5MasCaras();

    // Listar viviendas con precio superior al promedio general
    public List<Inmueble> listSobrePrecioPromedio();

    // Listar viviendas que tienen créditos asociados
    public List<Inmueble> listConCreditos();

    // Buscar viviendas por coincidencia en departamento, provincia o distrito
    public List<Inmueble> listByUbicacionGeneral(String keyword);
}