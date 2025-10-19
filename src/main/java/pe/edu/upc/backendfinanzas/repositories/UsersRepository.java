package pe.edu.upc.backendfinanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backendfinanzas.entities.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
    public Users findOneByUsername(String username);
}