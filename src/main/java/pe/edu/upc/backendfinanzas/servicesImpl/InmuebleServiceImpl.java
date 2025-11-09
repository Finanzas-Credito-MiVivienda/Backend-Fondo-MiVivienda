package pe.edu.upc.backendfinanzas.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backendfinanzas.entities.Inmueble;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;
import pe.edu.upc.backendfinanzas.repositories.InmuebleRepository;
import pe.edu.upc.backendfinanzas.services.InmuebleService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class InmuebleServiceImpl implements InmuebleService {
    @Autowired
    private InmuebleRepository iR;

    @Override
    public List<Inmueble> list() {
        return iR.findAll();
    }

    @Override
    public void insert(Inmueble ofertaInmueble) {
        iR.save(ofertaInmueble);
    }

    @Override
    public void update(Inmueble ofertaInmueble) {
        iR.save(ofertaInmueble);
    }

    @Override
    public Inmueble listId(int idInmueble) {
        return iR.findById(idInmueble).orElse(new Inmueble());
    }

    @Override
    public void delete(int idInmueble) {
        iR.deleteById(idInmueble);
    }

    //Buscar por tipo de vivienda
    @Override
    public List<Inmueble> listByTipo(TipoVivienda tipo) {
        return iR.findByTipoVivienda(tipo);
    }

    // Buscar por dirección exacta
    @Override
    public Optional<Inmueble> findByDireccion(String direccion) {
        return iR.findByDireccion(direccion);
    }

    // Buscar por dirección parcial
    @Override
    public List<Inmueble> listByDireccionParcial(String direccion) {
        return iR.findByDireccionContainingIgnoreCase(direccion);
    }

    // Buscar por rango de precio
    @Override
    public List<Inmueble> listByPrecioBetween(BigDecimal min, BigDecimal max) {
        return iR.findByPrecioVentaBetween(min, max);
    }

    // Buscar por área mínima
    @Override
    public List<Inmueble> listByAreaMinima(BigDecimal areaMin) {
        return iR.findByAreaM2GreaterThanEqual(areaMin);
    }

    // Validar existencia de dirección
    @Override
    public boolean existsByDireccion(String direccion) {
        return iR.existsByDireccion(direccion);
    }

    // Buscar por departamento y provincia
    @Override
    public List<Inmueble> listByDepartamentoAndProvincia(String departamento, String provincia) {
        return iR.findByDepartamentoAndProvinciaIgnoreCase(departamento, provincia);
    }

    // Buscar por distrito y rango de precio
    @Override
    public List<Inmueble> listByDistritoYRangoPrecio(String distrito, BigDecimal min, BigDecimal max) {
        return iR.buscarPorDistritoYRangoPrecio(distrito, min, max);
    }

    // Buscar por área mínima y tipo de vivienda
    @Override
    public List<Inmueble> listByAreaAndTipo(BigDecimal areaMin, TipoVivienda tipo) {
        return iR.buscarPorAreaYTipo(areaMin, tipo);
    }

    // Top 5 viviendas más costosas
    @Override
    public List<Inmueble> listTop5MasCaras() {
        return iR.top5MasCaras();
    }

    // Viviendas con precio superior al promedio
    @Override
    public List<Inmueble> listSobrePrecioPromedio() {
        return iR.viviendasSobrePrecioPromedio();
    }

    // Viviendas con créditos asociados
    @Override
    public List<Inmueble> listConCreditos() {
        return iR.viviendasConCreditos();
    }

    // Buscar por palabra clave en ubicación
    @Override
    public List<Inmueble> listByUbicacionGeneral(String keyword) {
        return iR.buscarPorUbicacionGeneral(keyword);
    }
}