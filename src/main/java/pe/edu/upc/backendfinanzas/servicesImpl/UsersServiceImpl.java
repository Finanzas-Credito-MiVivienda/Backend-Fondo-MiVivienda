package pe.edu.upc.backendfinanzas.servicesImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upc.backendfinanzas.dtos.request.UserLoginDTO;
import pe.edu.upc.backendfinanzas.dtos.request.UserRequestDTO;
import pe.edu.upc.backendfinanzas.dtos.response.UserResponseDTO;
import pe.edu.upc.backendfinanzas.entities.Role;
import pe.edu.upc.backendfinanzas.entities.Users;
import pe.edu.upc.backendfinanzas.exceptions.ResourceNotFoundException;
import pe.edu.upc.backendfinanzas.exceptions.ValidationException;
import pe.edu.upc.backendfinanzas.repositories.RoleRepository;
import pe.edu.upc.backendfinanzas.repositories.UsersRepository;
import pe.edu.upc.backendfinanzas.services.UsersService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Users createUser(Users user) {
        List<Role> managedRoles = new ArrayList<>();

        for (Role role : user.getRoles()) {
            Role managedRole = roleRepository.findByNameRol(role.getNameRol())
                    .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con nombre: " + role.getNameRol()));
            managedRoles.add(managedRole);
        }

        user.setRoles(managedRoles);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        validateUser(user);

        return usersRepository.save(user);
    }

    @Override
    public Users updateUser(int id, UserRequestDTO userRequestDTO) {
        Users usersFound = getUserById(id);
        if (usersFound != null) {
            LocalDate today = LocalDate.now();

            if(!userRequestDTO.getName().isBlank()){
                if(userRequestDTO.getName().length()<=2){
                    throw new ValidationException("El nombre del cliente no puede tener menos de tres caracteres");
                }
                usersFound.setName(userRequestDTO.getName());
            }

            if (!userRequestDTO.getLastName().isBlank()) {
                if (userRequestDTO.getLastName().length() <= 2) {
                    throw new ValidationException("El apellido no puede tener menos de tres caracteres");
                }
                usersFound.setLastName(userRequestDTO.getLastName());
            }

            if (!userRequestDTO.getAddress().isBlank()) {
                if (userRequestDTO.getAddress().length() < 5) {
                    throw new ValidationException("La dirección no puede tener menos de cinco caracteres");
                }
                usersFound.setAddress(userRequestDTO.getAddress());
            }

            if (!userRequestDTO.getEmail().isBlank()) {
                if (!userRequestDTO.getEmail().contains("@")) {
                    throw new ValidationException("Debe ingresar un correo válido");
                }
                usersFound.setEmail(userRequestDTO.getEmail());
            }

            if (userRequestDTO.getBirthday() != null) {
                if (userRequestDTO.getBirthday().isAfter(today.minusYears(18))) {
                    throw new ValidationException("Debe ser mayor de 18 años");
                }
                usersFound.setBirthday(userRequestDTO.getBirthday());
            }

            if (!userRequestDTO.getMaritalStatus().isBlank()) {
                usersFound.setMaritalStatus(userRequestDTO.getMaritalStatus());
            }

            if (!userRequestDTO.getPhoneNumber().isBlank()) {
                if (userRequestDTO.getPhoneNumber().length() != 9) {
                    throw new ValidationException("El número de teléfono debe tener 9 dígitos");
                }
                usersFound.setPhoneNumber(userRequestDTO.getPhoneNumber());
            }

            if (userRequestDTO.getGender() != null) {
                usersFound.setGender(userRequestDTO.getGender());
            }

            if (!userRequestDTO.getDni().isBlank()) {
                if (userRequestDTO.getDni().length() != 8) {
                    throw new ValidationException("El DNI debe tener 8 dígitos");
                }
                usersFound.setDni(userRequestDTO.getDni());
            }

            if (!userRequestDTO.getRuc().isBlank()) {
                if (userRequestDTO.getRuc().length() != 11) {
                    throw new ValidationException("El RUC debe tener 11 dígitos");
                }
                usersFound.setRuc(userRequestDTO.getRuc());
            }

            if (!userRequestDTO.getCurrencyType().isBlank()) {
                if (!userRequestDTO.getCurrencyType().equals("PEN") &&
                        !userRequestDTO.getCurrencyType().equals("USD")) {
                    throw new ValidationException("Tipo de moneda inválido");
                }
                usersFound.setCurrencyType(userRequestDTO.getCurrencyType());
            }

            if (userRequestDTO.getMonthlyIncome() != null) {
                if (userRequestDTO.getMonthlyIncome().doubleValue() < 0) {
                    throw new ValidationException("El ingreso no puede ser negativo");
                }
                usersFound.setMonthlyIncome(userRequestDTO.getMonthlyIncome());
            }

            return usersRepository.save(usersFound);
        }

        if (!usersRepository.existsById(id)) {
            throw new ResourceNotFoundException("El usuario no existe");
        }

        return null;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(u -> modelMapper.map(u, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO login(UserLoginDTO userLogin) {
        Users users = usersRepository.findByUsername(userLogin.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("No existe usuario con username: " + userLogin.getUsername()));

        return modelMapper.map(users, UserResponseDTO.class);
    }

    @Override
    public void deleteUser(int id) {
        if (!usersRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }

        usersRepository.deleteById(id);
    }

    @Override
    public Users getUserById(int id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        return users;
        //return modelMapper.map(users, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateCurrencyType(int id, String currencyType) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        if (currencyType == null || currencyType.isEmpty()) {
            throw new ValidationException("El tipo de moneda es obligatorio");
        }

        if (!currencyType.equals("USD") && !currencyType.equals("PEN")) {
            throw new ValidationException("Tipo de moneda no válido. Solo se permite 'USD' o 'PEN'");
        }

        users.setCurrencyType(currencyType);
        usersRepository.save(users);

        return modelMapper.map(users, UserResponseDTO.class);
    }

    @Override
    public Users findByUsername(String username) {
        Users users = usersRepository.findOneByUsername(username);
        return users;
    }

    @Override
    public Users findById(int id) {
        return usersRepository.findById(id).orElse(null);
    }

    private void validateUser(Users user) {
        if (usersRepository.existsById(user.getId())) {
            throw new ValidationException("El correo electronico ya esta registrado");
        }

        if (usersRepository.existsByEmailOrPhoneNumber(user.getEmail(), user.getPhoneNumber())) {
            throw new ValidationException("El correo o nombre ya existen");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new ValidationException("El usuario debe tener al menos un rol asignado");
        }

        for (Role role : user.getRoles()) {
            if (!role.getNameRol().equalsIgnoreCase("ADMIN") && !role.getNameRol().equalsIgnoreCase("CLIENT")) {
                throw new ValidationException("Rol no valido: " + role.getNameRol());
            }
        }
    }
}