package pe.edu.upc.backendfinanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.request.CreditoRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.CreditoResponseDTO;
import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.services.CreditoService;
import pe.edu.upc.backendfinanzas.services.InmuebleService;
import pe.edu.upc.backendfinanzas.services.UsersService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CreditoControllers {
    @Autowired
    private CreditoService creditoService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private InmuebleService inmuebleService;

    @Autowired
    private ModelMapper modelMapper;

    // Listar todos los créditos
    @GetMapping("/creditos")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CreditoResponseDTO> listar() {
        return creditoService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/creditos/generarCredito")
    public ResponseEntity<CreditoResponseDTO> generarCredito(@RequestBody CreditoRequestDTO dto){
        CreditoResponseDTO creditoResponseDTO = creditoService.calcularTasa(dto);
        return new ResponseEntity<>(creditoResponseDTO, HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/creditos/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public CreditoResponseDTO listarPorId(@PathVariable("id") int id) {
        return convertToDTO(creditoService.findById(id));
    }

    // Registrar crédito
    @PostMapping("/creditos")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void registrar(@RequestBody CreditoRequestDTO dto) {
        Credito credito = modelMapper.map(dto, Credito.class);
        credito.setUsuario(usersService.findById(dto.getUsuarioId()));
        credito.setInmueble(inmuebleService.listId(dto.getIdInmueble()));
        creditoService.insert(credito);
    }

    // Actualizar crédito
    @PutMapping("/creditos")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void modificar(@RequestBody CreditoRequestDTO dto) {
        Credito credito = modelMapper.map(dto, Credito.class);
        credito.setUsuario(usersService.findById(dto.getUsuarioId()));
        credito.setInmueble(inmuebleService.listId(dto.getIdInmueble()));
        creditoService.update(credito);
    }

    // Eliminar crédito
    @DeleteMapping("/creditos/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminar(@PathVariable("id") int id) {
        creditoService.delete(id);
    }

    @GetMapping("/creditos/usuario/{usuarioId}")
    public List<CreditoResponseDTO> listarPorUsuario(@PathVariable int usuarioId) {
        return creditoService.findByUsuarioId(usuarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/creditos/rango-monto")
    public List<CreditoResponseDTO> listarPorMonto(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return creditoService.findByMontoPrestamoBetween(min, max).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/creditos/sobre-promedio")
    public List<CreditoResponseDTO> listarSobrePromedio() {
        return creditoService.findCreditosSobrePromedio().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/creditos/departamento-provincia")
    public List<CreditoResponseDTO> listarPorUbicacion(@RequestParam String departamento,
                                                       @RequestParam String provincia) {
        return creditoService.findByDepartamentoOProvincia(departamento, provincia).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CreditoResponseDTO convertToDTO(Credito credito) {
        CreditoResponseDTO dto = modelMapper.map(credito, CreditoResponseDTO.class);
        dto.setNombreUsuario(credito.getUsuario().getUsername());
        dto.setDireccionInmueble(credito.getInmueble().getDireccion());
        return dto;
    }
}