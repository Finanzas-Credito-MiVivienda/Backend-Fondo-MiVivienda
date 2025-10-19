package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
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
@Table(name = "indicadores")
public class Indicador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "El Valor Actual es obligatorio")
    @Digits(integer = 15, fraction = 2, message = "El Valor Actual debe tener hasta 2 decimales")
    @Column(name = "valor_actual", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorActual; // Valor Actual Neto (VAN)

    @NotNull(message = "La Tasa Interna de Retorno es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La TIR debe ser mayor que 0")
    @Column(name = "tasa_interna_retorno", nullable = false, precision = 7, scale = 4)
    private BigDecimal tasaInternaRetorno; // Tasa Interna de Retorno (TIR)

    @NotNull(message = "El Costo Efectivo Total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El CET debe ser mayor que 0")
    @Column(name = "costo_efectivo_total", nullable = false, precision = 7, scale = 4)
    private BigDecimal costoEfectivoTotal; // Costo Efectivo Total (CET)

    @NotNull(message = "La Tasa Efectiva es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La tasa efectiva debe ser mayor que 0")
    @Column(name = "tasa_efectiva_anual", nullable = false, precision = 7, scale = 4)
    private BigDecimal tasaEfectiva; // Tasa Efectiva Anual (TEA)

    @NotNull(message = "La Tasa Efectiva Mensual es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La tasa efectiva mensual debe ser mayor que 0")
    @Column(name = "tasa_efectiva_mensual", nullable = false, precision = 7, scale = 4)
    private BigDecimal tasaEfectivaMensual; // Tasa Efectiva Mensual (TEM)

    @NotNull(message = "El Costo Total del Crédito es obligatorio")
    @Digits(integer = 15, fraction = 2, message = "El costo total debe tener hasta 2 decimales")
    @Column(name = "costo_total_credito", nullable = false, precision = 15, scale = 2)
    private BigDecimal costoTotalCredito; // Costo Total Credito (CTC)

    // Relación inversa hacia Crédito
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credito_id", nullable = false, unique = true)
    private Credito credito;
}