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
    private BigDecimal tea;

    @NotNull(message = "El seguro de desgravamen es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El seguro desgravamen debe ser mayor que 0")
    @Column(name = "seguro_desgravamen", nullable = false, precision = 7, scale = 4)
    private BigDecimal seguroDesgravamen;

    @NotNull(message = "El seguro de riesgo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El seguro riesgo debe ser mayor que 0")
    @Column(name = "seguro_riesgo", nullable = false, precision = 7, scale = 4)
    private BigDecimal seguroRiesgo;

    @NotNull(message = "Los gastos de administración son obligatorios")
    @Digits(integer = 15, fraction = 3, message = "Máximo 3 decimales permitidos")
    @Column(name = "gastos_administracion", nullable = false, precision = 15, scale = 3)
    private BigDecimal gastosAdministracion;

    @NotNull(message = "Los costes registrales son obligatorios")
    @Digits(integer = 15, fraction = 3, message = "Máximo 3 decimales permitidos")
    @Column(name = "costes_registrales", nullable = false, precision = 15, scale = 3)
    private BigDecimal costesRegistrales;

    @NotNull(message = "Los costes notariales son obligatorios")
    @Digits(integer = 15, fraction = 3, message = "Máximo 3 decimales permitidos")
    @Column(name = "costes_notariales", nullable = false, precision = 15, scale = 3)
    private BigDecimal costesNotariales;

    @NotNull(message = "El valor de tasación es obligatorio")
    @Digits(integer = 15, fraction = 3, message = "Máximo 3 decimales permitidos")
    @Column(name = "tasacion", nullable = false, precision = 15, scale = 3)
    private BigDecimal tasacion;

    @NotNull(message = "La comisión de estudio es obligatoria")
    @Digits(integer = 15, fraction = 3, message = "Máximo 3 decimales permitidos")
    @Column(name = "comision_estudio", nullable = false, precision = 15, scale = 3)
    private BigDecimal comisionEstudio;

    @NotNull(message = "La comisión de activación es obligatoria")
    @Digits(integer = 15, fraction = 3, message = "Máximo 3 decimales permitidos")
    @Column(name = "comision_activacion", nullable = false, precision = 15, scale = 3)
    private BigDecimal comisionActivacion;

    @NotNull(message = "El costo de portes es obligatorio")
    @Digits(integer = 15, fraction = 3, message = "Máximo 3 decimales permitidos")
    @Column(name = "portes", nullable = false, precision = 15, scale = 3)
    private BigDecimal portes;

    @NotNull(message = "La comisión periódica es obligatoria")
    @Digits(integer = 15, fraction = 3, message = "Máximo 3 decimales permitidos")
    @Column(name = "comision_periodica", nullable = false, precision = 15, scale = 3)
    private BigDecimal comisionPeriodica;

    @OneToOne(mappedBy = "entidadFinanciera", fetch = FetchType.LAZY)
    private Credito credito;
}