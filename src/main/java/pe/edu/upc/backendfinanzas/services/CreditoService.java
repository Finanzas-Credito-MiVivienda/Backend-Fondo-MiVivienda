package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.dtos.request.CreditoRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.CreditoResponseDTO;
import pe.edu.upc.backendfinanzas.entities.Credito;

import java.util.List;

public interface CreditoService {
    List<Credito> findAll();
    Credito insert(Credito credito);
    Credito update(Credito credito);
    void delete(int id);

    CreditoResponseDTO calcularCredito(CreditoRequestDTO dto);
    CreditoResponseDTO registrarCredito(CreditoRequestDTO dto);
}