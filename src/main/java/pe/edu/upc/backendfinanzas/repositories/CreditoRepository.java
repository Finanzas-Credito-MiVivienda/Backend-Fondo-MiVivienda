package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backendfinanzas.entities.Credito;

import java.util.List;
import java.util.Optional;

public interface CreditoRepository extends JpaRepository<Credito, Integer> {
    List<Credito> findByMoneda(String moneda);
    List<Credito> findByUsuarioId(Integer usuarioId);
    Optional<Credito> findById(Integer idCredito);
}