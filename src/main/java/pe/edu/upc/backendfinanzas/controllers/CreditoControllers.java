package pe.edu.upc.backendfinanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.request.CreditoRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.CreditoResponseDTO;
import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.services.CreditoService;
import pe.edu.upc.backendfinanzas.services.InmuebleService;
import pe.edu.upc.backendfinanzas.services.UsersService;

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

    @PostMapping("/creditos/calcular-credito")
    public ResponseEntity<CreditoResponseDTO> calcularCredito(@RequestBody CreditoRequestDTO dto) {
        CreditoResponseDTO response = creditoService.calcularCredito(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/creditos/registrar-credito")
    public ResponseEntity<CreditoResponseDTO> registrarCredito(@RequestBody CreditoRequestDTO dto) {
        CreditoResponseDTO response = creditoService.registrarCredito(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/creditos")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void modificar(@RequestBody CreditoRequestDTO dto) {
        Credito credito = modelMapper.map(dto, Credito.class);
        credito.setUsuario(usersService.findById(dto.getIdUsuario()));
        credito.setInmueble(inmuebleService.listId(dto.getIdInmueble()));
        creditoService.update(credito);
    }

    @DeleteMapping("/creditos/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminar(@PathVariable("id") int id) {
        creditoService.delete(id);
    }
}