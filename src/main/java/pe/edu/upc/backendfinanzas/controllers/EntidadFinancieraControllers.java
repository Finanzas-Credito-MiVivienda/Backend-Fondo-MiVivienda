package pe.edu.upc.backendfinanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.request.EntidadFinancieraRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.EntidadFinancieraResponseDTO;
import pe.edu.upc.backendfinanzas.entities.EntidadFinanciera;
import pe.edu.upc.backendfinanzas.services.EntidadFinancieraService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EntidadFinancieraControllers {
    @Autowired
    private EntidadFinancieraService eFService;

    @Autowired
    private ModelMapper modelMapper;

    // Listar todas las entidades financieras
    @GetMapping("/entidades-financieras")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<EntidadFinancieraResponseDTO> listar() {
        return eFService.list().stream()
                .map(ent -> modelMapper.map(ent, EntidadFinancieraResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Registrar una nueva entidad financiera
    @PostMapping("/entidades-financieras")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EntidadFinanciera> registrar(@RequestBody EntidadFinancieraRequestDTO dto) {
        EntidadFinanciera entidad = modelMapper.map(dto, EntidadFinanciera.class);
        return new ResponseEntity<>(eFService.insert(entidad), HttpStatus.OK);
    }

    // Modificar una entidad financiera existente
    @PutMapping("/entidades-financieras/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void modificar(@PathVariable("id") Integer id, @RequestBody EntidadFinancieraRequestDTO dto) {
        EntidadFinanciera entidad = modelMapper.map(dto, EntidadFinanciera.class);
        entidad.setId(id);
        eFService.update(entidad);
    }

    // Eliminar una entidad financiera
    @DeleteMapping("/entidades-financieras/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminar(@PathVariable("id") Integer id) {
        eFService.delete(id);
    }

    // Buscar por ID
    @GetMapping("/entidades-financieras/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public EntidadFinancieraResponseDTO listarPorId(@PathVariable("id") Integer id) {
        return modelMapper.map(eFService.listId(id), EntidadFinancieraResponseDTO.class);
    }

    // Buscar por nombre exacto
    @GetMapping("/entidades-financieras/nombre-exacto")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public EntidadFinancieraResponseDTO buscarPorNombre(@RequestParam String nombre) {
        return eFService.findByNombreEntidad(nombre)
                .map(ent -> modelMapper.map(ent, EntidadFinancieraResponseDTO.class))
                .orElse(null);
    }

    // Buscar por coincidencia parcial de nombre
    @GetMapping("/entidades-financieras/nombre")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<EntidadFinancieraResponseDTO> buscarPorNombreParcial(@RequestParam String nombre) {
        return eFService.listByNombreContaining(nombre).stream()
                .map(ent -> modelMapper.map(ent, EntidadFinancieraResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar por TEA dentro de un rango
    @GetMapping("/entidades-financieras/rango-tea")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<EntidadFinancieraResponseDTO> buscarPorRangoTea(@RequestParam BigDecimal min,
                                                                @RequestParam BigDecimal max) {
        return eFService.listByTeaBetween(min, max).stream()
                .map(ent -> modelMapper.map(ent, EntidadFinancieraResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar entidades con gastos de administración menores a un valor
    @GetMapping("/entidades-financieras/gastos-menores")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<EntidadFinancieraResponseDTO> buscarPorGastosMenores(@RequestParam BigDecimal valor) {
        return eFService.listByGastosAdministracionLessThan(valor).stream()
                .map(ent -> modelMapper.map(ent, EntidadFinancieraResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar entidades con seguro de desgravamen menor a un valor (consulta @Query)
    @GetMapping("/entidades-financieras/seguro-menor")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<EntidadFinancieraResponseDTO> buscarPorSeguroDesgravamenMenor(@RequestParam BigDecimal valor) {
        return eFService.buscarPorSeguroDesgravamenMenorA(valor).stream()
                .map(ent -> modelMapper.map(ent, EntidadFinancieraResponseDTO.class))
                .collect(Collectors.toList());
    }
}