package pe.edu.upc.backendfinanzas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backendfinanzas.dtos.TokenDTO;
import pe.edu.upc.backendfinanzas.dtos.request.UserLoginDTO;
import pe.edu.upc.backendfinanzas.dtos.request.UserRequestDTO;
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

    @Transactional(readOnly = true)
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = usersService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        Users users = usersService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/users/registrar")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
        Users createdUser = usersService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        usersService.deleteUser(id);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/users/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
        SecurityUser securityUser = new SecurityUser(usersService.findByUsername(userLoginDTO.getUsername()));
        String jwt = jwtUtilService.generateToken(securityUser);
        int user_id = securityUser.getUser().getId();

        String authString = securityUser.getUser().getRoles().stream().map(a->a.getNameRol()).collect(Collectors.joining(";"));

        return new ResponseEntity<>(new TokenDTO(jwt,user_id,authString),HttpStatus.OK);
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable("id") int id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        Users updatedUser = usersService.updateUser(id, userRequestDTO);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Transactional
    @PutMapping("/users/{id}/currency-type/{currencyType}")
    public ResponseEntity<UserResponseDTO> updateCurrencyType(@PathVariable int id, @PathVariable String currencyType) {
        UserResponseDTO updatedUser = usersService.updateCurrencyType(id, currencyType);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}