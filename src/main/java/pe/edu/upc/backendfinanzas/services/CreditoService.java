package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.dtos.request.CreditoRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.CreditoResponseDTO;
import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.entities.TipoPeriodoGracia;
import pe.edu.upc.backendfinanzas.entities.TipoTasaInteres;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CreditoService {
    List<Credito> findAll();
    Credito findById(int id);
    Credito insert(Credito credito);
    Credito update(Credito credito);
    void delete(int id);

    List<Credito> findByUsuarioId(int usuarioId);
    List<Credito> findByMontoPrestamoBetween(BigDecimal min, BigDecimal max);
    List<Credito> findByDepartamentoOProvincia(String departamento, String provincia);
    List<Credito> findCreditosSobrePromedio();

    // Calcular tasa
    CreditoResponseDTO calcularTasa(CreditoRequestDTO dto);
}