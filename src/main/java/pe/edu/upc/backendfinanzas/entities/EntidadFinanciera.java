package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entidadesFinancieras")
public class EntidadFinanciera {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @NotBlank(message = "El nombre de la entidad es obligatorio")
    @Column(name = "nombre_entidad", nullable = false, length = 100)
    private String nombreEntidad;

    @NotNull(message = "La Tasa Efectiva Anual (TEA) es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La TEA debe ser mayor que 0")
    @Column(name = "tea", nullable = false, precision = 7, scale = 4)
    private BigDecimal TEA;

    @NotNull(message = "El Seguro de Desgravamen es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El seguro de desgravamen debe ser mayor que 0")
    @Column(name = "seguro_desgravamen", nullable = false, precision = 7, scale = 4)
    private BigDecimal seguroDesgravamen;

    @NotNull(message = "El Seguro de Riesgo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El seguro de riesgo debe ser mayor que 0")
    @Column(name = "seguro_riesgo", nullable = false, precision = 7, scale = 4)
    private BigDecimal seguroRiesgo;

    @NotNull(message = "Los gastos de administración son obligatorios")
    @Digits(integer = 15, fraction = 2, message = "El valor debe tener hasta 2 decimales")
    @Column(name = "gastos_administracion", nullable = false, precision = 15, scale = 2)
    private BigDecimal gastosAdministracion;

    @NotNull(message = "El costo de fotocopias es obligatorio")
    @Digits(integer = 15, fraction = 2, message = "El valor debe tener hasta 2 decimales")
    @Column(name = "fotocopias", nullable = false, precision = 15, scale = 2)
    private BigDecimal fotocopias;

    @NotNull(message = "El costo de portes es obligatorio")
    @Digits(integer = 15, fraction = 2, message = "El valor debe tener hasta 2 decimales")
    @Column(name = "portes", nullable = false, precision = 15, scale = 2)
    private BigDecimal portes;

    @NotNull(message = "La comisión periódica es obligatoria")
    @Digits(integer = 15, fraction = 2, message = "El valor debe tener hasta 2 decimales")
    @Column(name = "comision_periodica", nullable = false, precision = 15, scale = 2)
    private BigDecimal comisionPeriodica;

    // Relación con la entidad CREDITO
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credito_id", nullable = false, unique = true)
    private Credito credito;
}