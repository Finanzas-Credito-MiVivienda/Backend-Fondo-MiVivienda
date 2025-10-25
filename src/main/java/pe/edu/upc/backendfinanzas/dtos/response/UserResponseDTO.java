package pe.edu.upc.backendfinanzas.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private int id;
    private String name;
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
    private String authToken;
    private String token; // Token
}