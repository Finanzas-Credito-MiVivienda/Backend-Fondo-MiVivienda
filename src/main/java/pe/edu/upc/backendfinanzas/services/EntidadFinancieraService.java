package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.entities.EntidadFinanciera;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EntidadFinancieraService {
    EntidadFinanciera insert(EntidadFinanciera entidadFinanciera);
    void update(EntidadFinanciera entidadFinanciera);
    void delete(int id);
    List<EntidadFinanciera> list();
    EntidadFinanciera listId(int id);

    Optional<EntidadFinanciera> findByNombreEntidad(String nombreEntidad);
    List<EntidadFinanciera> listByNombreContaining(String nombreEntidad);
    List<EntidadFinanciera> listByTeaBetween(BigDecimal min, BigDecimal max);
    List<EntidadFinanciera> listByGastosAdministracionLessThan(BigDecimal valor);
    List<EntidadFinanciera> buscarPorSeguroDesgravamenMenorA(BigDecimal valor);
}