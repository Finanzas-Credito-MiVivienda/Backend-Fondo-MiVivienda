package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backendfinanzas.entities.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

}