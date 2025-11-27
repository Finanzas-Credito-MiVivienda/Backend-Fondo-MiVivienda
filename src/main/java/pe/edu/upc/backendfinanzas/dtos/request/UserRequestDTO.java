package pe.edu.upc.backendfinanzas.dtos.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String name;
    private String lastName;
    private String address;
    private String email;
    private LocalDate birthday;
    private String maritalStatus;
    private String phoneNumber;
    private Character gender;
    private String dni;
    private String ruc;
    private String currencyType;
    private BigDecimal monthlyIncome;
}