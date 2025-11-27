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

    List<Inmueble> listByTipo(TipoVivienda tipo);
    Optional<Inmueble> findByDireccion(String direccion);
    List<Inmueble> listByDireccionParcial(String direccion);
    List<Inmueble> listByPrecioBetween(BigDecimal min, BigDecimal max);
    List<Inmueble> listByAreaMinima(BigDecimal areaMin);
    boolean existsByDireccion(String direccion);
    List<Inmueble> listByDepartamentoAndProvincia(String departamento, String provincia);
    List<Inmueble> listByDistritoYRangoPrecio(String distrito, BigDecimal min, BigDecimal max);
    List<Inmueble> listByAreaAndTipo(BigDecimal areaMin, TipoVivienda tipo);
    List<Inmueble> listTop5MasCaras();
    List<Inmueble> listSobrePrecioPromedio();
    List<Inmueble> listConCreditos();
    List<Inmueble> listByUbicacionGeneral(String keyword);
}