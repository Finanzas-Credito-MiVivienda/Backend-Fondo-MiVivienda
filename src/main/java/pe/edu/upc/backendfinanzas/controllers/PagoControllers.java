package pe.edu.upc.backendfinanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.PlanPagosCronogramaDTO;
import pe.edu.upc.backendfinanzas.dtos.PlanPagosTotalesDTO;
import pe.edu.upc.backendfinanzas.entities.Pago;
import pe.edu.upc.backendfinanzas.services.PagoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PagoControllers {
    @Autowired
    private PagoService pagoService;

    @Autowired
    private ModelMapper modelMapper;

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

    @DeleteMapping("/pagos/{idPago}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable("idPago") int idPago) {
        pagoService.delete(idPago);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/pagos/{idPago}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public ResponseEntity<PlanPagosCronogramaDTO> listarPorId(@PathVariable("idPago") int idPago) {
        Pago pago = pagoService.findById(idPago);
        PlanPagosCronogramaDTO respuesta = modelMapper.map(pago, PlanPagosCronogramaDTO.class);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/pagos/generar-cronograma/{idCredito}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public List<PlanPagosCronogramaDTO> generarCronograma(@PathVariable int idCredito) {
        return pagoService.generarCronograma(idCredito).stream()
                .map(p -> modelMapper.map(p, PlanPagosCronogramaDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/pagos/cronograma/{idCredito}/totales")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public ResponseEntity<PlanPagosTotalesDTO> obtenerTotalesCronograma(@PathVariable int idCredito) {
        PlanPagosTotalesDTO totales = pagoService.calcularTotalesCronograma(idCredito);
        return ResponseEntity.ok(totales);
    }
}