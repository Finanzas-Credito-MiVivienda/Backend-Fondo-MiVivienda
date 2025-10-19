package pe.edu.upc.backendfinanzas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 40, message = "Name must be between 2 and 40 characters")
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @NotBlank(message = "Last Name is required")
    @Size(min = 2, max = 40, message = "Last Name must be between 2 and 40 characters")
    @Column(name = "lastName", nullable = false, length = 40)
    private String lastName;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 50, message = "Address must be between 5 and 50 characters")
    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 40, message = "Email must be between 5 and 40 characters")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true, length = 40)
    private String email;

    @NotNull(message = "Birthday is mandatory")
    @Past(message = "Birthday must be a date in the past")
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @NotBlank(message = "Marital status is required")
    @Size(min = 4, max = 15, message = "Marital status must be between 4 and 15 characters")
    @Column(name = "marital_status", nullable = false, length = 15)
    private String maritalStatus;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{9}", message = "Phone number must be exactly 9 digits")
    @Column(name = "phone_number", nullable = false, length = 9, columnDefinition = "CHAR(9)")
    private String phoneNumber;

    @NotNull(message = "Gender is required")
    @Column(name = "gender", nullable = false)
    private Character gender;

    @NotBlank(message = "DNI is required")
    @Pattern(regexp = "\\d{8}", message = "DNI must be exactly 8 digits")
    @Column(name = "dni", nullable = false, unique = true, length = 8, columnDefinition = "CHAR(8)")
    private String dni;

    @NotBlank(message = "RUC is required")
    @Pattern(regexp = "\\d{11}", message = "RUC must be exactly 11 digits")
    @Column(name = "ruc", nullable = false, unique = true, length = 11, columnDefinition = "CHAR(11)")
    private String ruc;

    @NotBlank(message = "Currency type is required")
    @Size(min = 3, max = 3, message = "Currency type must be 3 characters")
    @Column(name = "currency_type", nullable = false, length =  3, columnDefinition = "CHAR(3)")
    private String currencyType;

    @NotNull(message = "Monthly income is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly income must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Monthly income must be a valid amount")
    @Column(name = "monthly_income", nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyIncome;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(
            name = "user_by_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Credito> creditos;
}