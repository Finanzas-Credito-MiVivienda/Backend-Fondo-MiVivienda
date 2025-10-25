package pe.edu.upc.backendfinanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.InmuebleDTO;
import pe.edu.upc.backendfinanzas.entities.Inmueble;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;
import pe.edu.upc.backendfinanzas.services.InmuebleService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Inmueble")
public class InmuebleControllers {
    @Autowired
    private InmuebleService iS;

    // Listar todos los inmuebles (ADMIN y CLIENT)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> listar() {
        return iS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Registrar inmueble (solo ADMIN)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void registrar(@RequestBody InmuebleDTO dto) {
        ModelMapper m = new ModelMapper();
        Inmueble inmueble = m.map(dto, Inmueble.class);
        iS.insert(inmueble);
    }

    // Modificar inmueble (solo ADMIN)
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void modificar(@RequestBody InmuebleDTO dto) {
        ModelMapper m = new ModelMapper();
        Inmueble inmueble = m.map(dto, Inmueble.class);
        iS.update(inmueble);
    }

    // Eliminar inmueble (solo ADMIN)
    @DeleteMapping("/{idInmueble}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminar(@PathVariable("idInmueble") Integer idInmueble) {
        iS.delete(idInmueble);
    }

    // Buscar inmueble por ID (ADMIN y CLIENT)
    @GetMapping("/{idInmueble}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public InmuebleDTO listarId(@PathVariable("idInmueble") Integer idInmueble) {
        ModelMapper m = new ModelMapper();
        return m.map(iS.listId(idInmueble), InmuebleDTO.class);
    }

    // Buscar por tipo de vivienda
    @GetMapping("/tipo")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> buscarPorTipo(@RequestParam TipoVivienda tipo) {
        return iS.listByTipo(tipo).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Buscar por dirección exacta
    @GetMapping("/direccionExacta")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public InmuebleDTO buscarPorDireccion(@RequestParam String direccion) {
        ModelMapper m = new ModelMapper();
        return iS.findByDireccion(direccion)
                .map(x -> m.map(x, InmuebleDTO.class))
                .orElse(null);
    }

    // Buscar por palabra clave en dirección
    @GetMapping("/direccion")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> buscarPorDireccionParcial(@RequestParam String direccion) {
        return iS.listByDireccionParcial(direccion).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Buscar por rango de precios
    @GetMapping("/rango-precio")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> buscarPorRangoPrecio(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return iS.listByPrecioBetween(min, max).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Buscar por área mínima
    @GetMapping("/area-minima")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> buscarPorAreaMinima(@RequestParam BigDecimal areaMin) {
        return iS.listByAreaMinima(areaMin).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Buscar por departamento y provincia
    @GetMapping("/ubicacion")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> buscarPorDepProv(@RequestParam String departamento, @RequestParam String provincia) {
        return iS.listByDepartamentoAndProvincia(departamento, provincia).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Buscar por distrito y rango de precio
    @GetMapping("/distrito-rango")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> buscarPorDistritoYRango(@RequestParam String distrito,
                                                     @RequestParam BigDecimal min,
                                                     @RequestParam BigDecimal max) {
        return iS.listByDistritoYRangoPrecio(distrito, min, max).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Buscar por área mínima y tipo
    @GetMapping("/area-tipo")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> buscarPorAreaYTipo(@RequestParam BigDecimal areaMin,
                                                @RequestParam TipoVivienda tipo) {
        return iS.listByAreaAndTipo(areaMin, tipo).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Top 5 viviendas más costosas
    @GetMapping("/top5")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> listarTop5() {
        return iS.listTop5MasCaras().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Viviendas con precio superior al promedio
    @GetMapping("/sobre-promedio")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<InmuebleDTO> listarSobrePromedio() {
        return iS.listSobrePrecioPromedio().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Viviendas con créditos asociados
    @GetMapping("/con-credito")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<InmuebleDTO> listarConCredito() {
        return iS.listConCreditos().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }

    // Buscar viviendas por palabra clave en ubicación
    @GetMapping("/buscar")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleDTO> buscarPorUbicacion(@RequestParam String keyword) {
        return iS.listByUbicacionGeneral(keyword).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, InmuebleDTO.class);
        }).collect(Collectors.toList());
    }
}