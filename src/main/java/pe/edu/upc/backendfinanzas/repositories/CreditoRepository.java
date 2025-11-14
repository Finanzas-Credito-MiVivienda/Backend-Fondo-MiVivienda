package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backendfinanzas.entities.Credito;

import java.util.List;

public interface CreditoRepository extends JpaRepository<Credito, Integer> {
    // Buscar por departamento o provincia del inmueble
    @Query("SELECT c FROM Credito c WHERE c.inmueble.departamento = :departamento OR c.inmueble.provincia = :provincia")
    List<Credito> findByDepartamentoOProvincia(@Param("departamento") String departamento,
                                               @Param("provincia") String provincia);

    // Créditos con monto mayor al promedio general
    @Query("SELECT c FROM Credito c WHERE c.montoPrestamo > (SELECT AVG(c2.montoPrestamo) FROM Credito c2)")
    List<Credito> findCreditosSobrePromedio();
}