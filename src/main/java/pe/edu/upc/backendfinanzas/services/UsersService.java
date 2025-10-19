package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.dtos.request.UserLoginDTO;
import pe.edu.upc.backendfinanzas.dtos.response.UserResponseDTO;
import pe.edu.upc.backendfinanzas.entities.Users;

import java.util.List;

public interface UsersService {
    Users createUser(Users user);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO login(UserLoginDTO userLogin);
    void deleteUser(int id);
    UserResponseDTO getUserById(int id);
    UserResponseDTO updateCurrencyType(int id, String currencyType);
    Users findByUsername(String username);
}