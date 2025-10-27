package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inmuebles")
public class Inmueble {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @NotNull(message = "El área en m2 es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "El área debe ser mayor que 0")
    @Column(name = "area_m2", nullable = false, precision = 10, scale = 2)
    private BigDecimal areaM2;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column(name = "precio_venta", nullable = false, precision = 15, scale = 2)
    private BigDecimal precioVenta;

    @NotNull(message = "El departamento es obligatorio")
    @Size(min = 5, max = 50, message = "El departamento debe tener entre 5 y 50 caracteres")
    @Column(name = "departamento", nullable = false, length = 50)
    private String departamento;

    @NotNull(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @NotNull(message = "El tipo de vivienda es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vivienda", nullable = false, length = 50)
    private TipoVivienda tipoDeVivienda;  // CASA, DEPARTAMENTO, TERRENO

    @NotBlank(message = "La provincia es obligatoria")
    @Size(min = 5, max = 50, message = "La provincia debe tener entre 5 y 50 caracteres")
    @Column(name = "provincia", nullable = false, length = 50)
    private String provincia;

    @NotBlank(message = "El distrito es obligatorio")
    @Size(min = 5, max = 50, message = "El distrito debe tener entre 5 y 50 caracteres")
    @Column(name = "distrito", nullable = false, length = 50)
    private String distrito;

    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL)
    private List<Credito> creditos;
}