package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.dtos.IndicadorDTO;
import pe.edu.upc.backendfinanzas.dtos.PlanPagosCronogramaDTO;
import pe.edu.upc.backendfinanzas.entities.Credito;

import java.util.List;

public interface IndicadorService {
    IndicadorDTO calcularIndicadorPorCronograma(List<PlanPagosCronogramaDTO> cronograma, Credito credito);
}