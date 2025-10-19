package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backendfinanzas.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNameRol(String nameRol);
}