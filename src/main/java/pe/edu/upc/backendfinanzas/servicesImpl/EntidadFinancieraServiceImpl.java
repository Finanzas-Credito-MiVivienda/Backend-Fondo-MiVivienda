package pe.edu.upc.backendfinanzas.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backendfinanzas.entities.EntidadFinanciera;
import pe.edu.upc.backendfinanzas.repositories.EntidadFinancieraRepository;
import pe.edu.upc.backendfinanzas.services.EntidadFinancieraService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class EntidadFinancieraServiceImpl implements EntidadFinancieraService {
    @Autowired
    private EntidadFinancieraRepository entidadFinancieraRepository;

    @Override
    public EntidadFinanciera insert(EntidadFinanciera entidadFinanciera) {
        return entidadFinancieraRepository.save(entidadFinanciera);
    }

    @Override
    public void update(EntidadFinanciera entidadFinanciera) {
        entidadFinancieraRepository.save(entidadFinanciera);
    }

    @Override
    public void delete(int id) {
        entidadFinancieraRepository.deleteById(id);
    }

    @Override
    public List<EntidadFinanciera> list() {
        return entidadFinancieraRepository.findAll();
    }

    @Override
    public EntidadFinanciera listId(int id) {
        return entidadFinancieraRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<EntidadFinanciera> findByNombreEntidad(String nombreEntidad) {
        return entidadFinancieraRepository.findByNombreEntidad(nombreEntidad);
    }

    @Override
    public List<EntidadFinanciera> listByNombreContaining(String nombreEntidad) {
        return entidadFinancieraRepository.findByNombreEntidadContainingIgnoreCase(nombreEntidad);
    }

    @Override
    public List<EntidadFinanciera> listByTeaBetween(BigDecimal min, BigDecimal max) {
        return entidadFinancieraRepository.findByTeaBetween(min, max);
    }

    @Override
    public List<EntidadFinanciera> listByGastosAdministracionLessThan(BigDecimal valor) {
        return entidadFinancieraRepository.findByGastosAdministracionLessThan(valor);
    }

    @Override
    public List<EntidadFinanciera> buscarPorSeguroDesgravamenMenorA(BigDecimal valor) {
        return entidadFinancieraRepository.buscarPorSeguroDesgravamenMenorA(valor);
    }
}