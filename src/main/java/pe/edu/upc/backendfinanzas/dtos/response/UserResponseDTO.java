package pe.edu.upc.backendfinanzas.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.backendfinanzas.dtos.RoleDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String username;
    private String birthday;
    private String phoneNumber;
    private String dni;
    private Character gender;
    private String ruc;
    private String currencyType;
    //private List<RoleDTO> roles;
    private String authToken;

    // Token
    private String token;
}