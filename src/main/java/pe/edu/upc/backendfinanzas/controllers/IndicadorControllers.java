package pe.edu.upc.backendfinanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.IndicadorDTO;
import pe.edu.upc.backendfinanzas.dtos.PlanPagosCronogramaDTO;
import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.services.IndicadorService;
import pe.edu.upc.backendfinanzas.services.PagoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class IndicadorControllers {
    @Autowired
    private IndicadorService indicadorService;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/indicadores/generar-indicadores/{idCredito}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    public ResponseEntity<IndicadorDTO> obtenerIndicadoresPorCredito(@PathVariable int idCredito) {
        List<PlanPagosCronogramaDTO> cronograma = pagoService.generarCronograma(idCredito);
        if (cronograma == null || cronograma.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Credito credito = pagoService.obtenerCreditoPorId(idCredito);
        if (credito == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        IndicadorDTO indicadores = indicadorService.calcularIndicadorPorCronograma(cronograma, credito);

        return new ResponseEntity<>(indicadores, HttpStatus.OK);
    }
}