package pe.edu.upc.backendfinanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.request.InmuebleRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.InmuebleResponseDTO;
import pe.edu.upc.backendfinanzas.entities.Inmueble;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;
import pe.edu.upc.backendfinanzas.services.InmuebleService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class InmuebleControllers {
    @Autowired
    private InmuebleService iS;

    @Autowired
    private ModelMapper modelMapper;

    // Listar todos los inmuebles
    @GetMapping("/inmuebles")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> listar() {
        return iS.list().stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Registrar inmueble
    @PostMapping("/inmuebles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void registrar(@RequestBody InmuebleRequestDTO dto) {
        Inmueble ofertaInmueble = modelMapper.map(dto, Inmueble.class);
        iS.insert(ofertaInmueble);
    }

    // Modificar inmueble
    @PutMapping("/inmuebles/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void modificar(@PathVariable("id") Integer id, @RequestBody InmuebleRequestDTO dto) {
        Inmueble ofertaInmueble = modelMapper.map(dto, Inmueble.class);
        ofertaInmueble.setId(id);
        iS.update(ofertaInmueble);
    }

    // Eliminar inmueble
    @DeleteMapping("/inmuebles/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminar(@PathVariable("id") Integer id) {
        iS.delete(id);
    }

    // Buscar por ID
    @GetMapping("/inmuebles/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public InmuebleResponseDTO listarId(@PathVariable("id") Integer id) {
        return modelMapper.map(iS.listId(id), InmuebleResponseDTO.class);
    }

    // Buscar por tipo de vivienda
    @GetMapping("/inmuebles/tipo")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> buscarPorTipo(@RequestParam TipoVivienda tipo) {
        return iS.listByTipo(tipo).stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar por dirección exacta
    @GetMapping("/inmuebles/direccionExacta")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public InmuebleResponseDTO buscarPorDireccion(@RequestParam String direccion) {
        return iS.findByDireccion(direccion)
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .orElse(null);
    }

    // Buscar por palabra clave en dirección
    @GetMapping("/inmuebles/direccion")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> buscarPorDireccionParcial(@RequestParam String direccion) {
        return iS.listByDireccionParcial(direccion).stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar por rango de precios
    @GetMapping("/inmuebles/rango-precio")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> buscarPorRangoPrecio(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return iS.listByPrecioBetween(min, max).stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar por área mínima
    @GetMapping("/inmuebles/area-minima")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> buscarPorAreaMinima(@RequestParam BigDecimal areaMin) {
        return iS.listByAreaMinima(areaMin).stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar por departamento y provincia
    @GetMapping("/inmuebles/ubicacion")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> buscarPorDepProv(@RequestParam String departamento, @RequestParam String provincia) {
        return iS.listByDepartamentoAndProvincia(departamento, provincia).stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar por distrito y rango de precio
    @GetMapping("/inmuebles/distrito-rango")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> buscarPorDistritoYRango(@RequestParam String distrito,
                                                             @RequestParam BigDecimal min,
                                                             @RequestParam BigDecimal max) {
        return iS.listByDistritoYRangoPrecio(distrito, min, max).stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar por área mínima y tipo
    @GetMapping("/inmuebles/area-tipo")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> buscarPorAreaYTipo(@RequestParam BigDecimal areaMin,
                                                        @RequestParam TipoVivienda tipo) {
        return iS.listByAreaAndTipo(areaMin, tipo).stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Top 5 viviendas más costosas
    @GetMapping("/inmuebles/top5")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> listarTop5() {
        return iS.listTop5MasCaras().stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Viviendas con precio superior al promedio
    @GetMapping("/inmuebles/sobre-promedio")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<InmuebleResponseDTO> listarSobrePromedio() {
        return iS.listSobrePrecioPromedio().stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Viviendas con créditos asociados
    @GetMapping("/inmuebles/con-credito")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<InmuebleResponseDTO> listarConCredito() {
        return iS.listConCreditos().stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar viviendas por palabra clave en ubicación
    @GetMapping("/inmuebles/buscar")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<InmuebleResponseDTO> buscarPorUbicacion(@RequestParam String keyword) {
        return iS.listByUbicacionGeneral(keyword).stream()
                .map(x -> modelMapper.map(x, InmuebleResponseDTO.class))
                .collect(Collectors.toList());
    }
}