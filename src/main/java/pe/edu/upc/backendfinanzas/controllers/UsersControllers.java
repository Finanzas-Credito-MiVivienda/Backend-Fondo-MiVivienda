package pe.edu.upc.backendfinanzas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.request.UserLoginDTO;
import pe.edu.upc.backendfinanzas.dtos.response.UserResponseDTO;
import pe.edu.upc.backendfinanzas.entities.Users;
import pe.edu.upc.backendfinanzas.security.JwtUtilService;
import pe.edu.upc.backendfinanzas.security.SecurityUser;
import pe.edu.upc.backendfinanzas.services.UsersService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class UsersControllers {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilService jwtUtilService;

    @Autowired
    private UsersService usersService;

    // URL: http://localhost:8080/api/v1/users
    @Transactional(readOnly = true)
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = usersService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/users/{id}
    @Transactional(readOnly = true)
    @GetMapping("users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(int id) {
        UserResponseDTO user = usersService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/users
    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
        Users createdUser = usersService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/users/{id}
    @Transactional
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        usersService.deleteUser(id);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/users/login
    @Transactional
    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        UserResponseDTO response = usersService.login(userLoginDTO);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUsername(), userLoginDTO.getPassword())
        );
        SecurityUser securityUser = new SecurityUser(usersService.findByUsername(userLoginDTO.getUsername()));
        String jwt = jwtUtilService.generateToken(securityUser);

        String authString = securityUser.getUser().getRoles().stream().map(a->a.getNameRol()).collect(Collectors.joining(";"));

        response.setToken(jwt);
        response.setAuthToken(authString);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/users/{id}/currency-type/{currencyType}
    @Transactional
    @PutMapping("/users/{id}/currency-type/{currencyType}")
    public ResponseEntity<UserResponseDTO> updateCurrencyType(@PathVariable int id, @PathVariable String currencyType) {
        UserResponseDTO updatedUser = usersService.updateCurrencyType(id, currencyType);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}