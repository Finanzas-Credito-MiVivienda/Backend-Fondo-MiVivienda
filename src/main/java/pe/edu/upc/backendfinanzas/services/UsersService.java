package pe.edu.upc.backendfinanzas.services;

import pe.edu.upc.backendfinanzas.dtos.request.UserLoginDTO;
import pe.edu.upc.backendfinanzas.dtos.request.UserRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.UserResponseDTO;
import pe.edu.upc.backendfinanzas.entities.Users;

import java.util.List;

public interface UsersService {
    Users createUser(Users user);
    Users updateUser(int id, UserRequestDTO userRequestDTO);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO login(UserLoginDTO userLogin);
    void deleteUser(int id);
    Users getUserById(int id);
    UserResponseDTO updateCurrencyType(int id, String currencyType);
    Users findByUsername(String username);
    Users findById(int id);
}