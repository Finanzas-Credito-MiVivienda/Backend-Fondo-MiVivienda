package pe.edu.upc.backendfinanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.PlanPagosCronogramaDTO;
import pe.edu.upc.backendfinanzas.entities.Pago;
import pe.edu.upc.backendfinanzas.services.PagoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PagoControllers {
    @Autowired
    private PagoService pagoService;

    @Autowired
    private ModelMapper modelMapper;

    // Listar todos los pagos
    @GetMapping("/pagos")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<PlanPagosCronogramaDTO> listar() {
        return pagoService.findAll().stream()
                .map(pago -> modelMapper.map(pago, PlanPagosCronogramaDTO.class))
                .collect(Collectors.toList());
    }

    // Registrar nuevo pago
    @PostMapping("/pagos")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PlanPagosCronogramaDTO> registrar(@RequestBody PlanPagosCronogramaDTO dto) {
        Pago pago = modelMapper.map(dto, Pago.class);
        Pago nuevoPago = pagoService.insert(pago);
        PlanPagosCronogramaDTO respuesta = modelMapper.map(nuevoPago, PlanPagosCronogramaDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // Modificar pago existente
    @PutMapping("/pagos/{idPago}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PlanPagosCronogramaDTO> modificar(@PathVariable("idPago") int idPago,
                                                     @RequestBody PlanPagosCronogramaDTO dto) {
        Pago pago = modelMapper.map(dto, Pago.class);
        pago.setId(idPago);
        Pago actualizado = pagoService.update(pago);
        PlanPagosCronogramaDTO respuesta = modelMapper.map(actualizado, PlanPagosCronogramaDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // Eliminar pago
    @DeleteMapping("/pagos/{idPago}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable("idPago") int idPago) {
        pagoService.delete(idPago);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Buscar pago por ID
    @GetMapping("/pagos/{idPago}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public ResponseEntity<PlanPagosCronogramaDTO> listarPorId(@PathVariable("idPago") int idPago) {
        Pago pago = pagoService.findById(idPago);
        PlanPagosCronogramaDTO respuesta = modelMapper.map(pago, PlanPagosCronogramaDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // Buscar pagos por crédito
    @GetMapping("/pagos/credito/{idCredito}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<PlanPagosCronogramaDTO> listarPorCredito(@PathVariable("idCredito") int idCredito) {
        return pagoService.findByCredito(idCredito).stream()
                .map(p -> modelMapper.map(p, PlanPagosCronogramaDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar pagos entre fechas
    @GetMapping("/pagos/rango-fechas")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<PlanPagosCronogramaDTO> buscarEntreFechas(@RequestParam LocalDate inicio,
                                                   @RequestParam LocalDate fin) {
        return pagoService.findByFechaBetween(inicio, fin).stream()
                .map(p -> modelMapper.map(p, PlanPagosCronogramaDTO.class))
                .collect(Collectors.toList());
    }

    // Generar flujo de pagos por crédito
    @GetMapping("/pagos/generar/{idCredito}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<PlanPagosCronogramaDTO> generarPagos(@PathVariable("idCredito") int idCredito) {
        return pagoService.generarPagosPorCredito(idCredito).stream()
                .map(p -> modelMapper.map(p, PlanPagosCronogramaDTO.class))
                .collect(Collectors.toList());
    }
}