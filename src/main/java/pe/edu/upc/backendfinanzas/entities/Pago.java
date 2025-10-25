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
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @NotNull(message = "El número de cuota es obligatorio")
    @Column(name = "numero_cuota", nullable = false)
    private Integer numeroCuota;

    @NotNull(message = "La fecha de pago es obligatoria")
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @NotNull(message = "El saldo inicial es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo inicial no puede ser negativo")
    @Digits(integer = 15, fraction = 2, message = "El saldo inicial debe tener hasta 2 decimales")
    @Column(name = "saldo_inicial", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoInicial;

    @NotNull(message = "El interés es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El interés no puede ser negativo")
    @Digits(integer = 10, fraction = 2, message = "El interés debe tener hasta 2 decimales")
    @Column(name = "interes", nullable = false, precision = 10, scale = 2)
    private BigDecimal interes;

    @NotNull(message = "La amortización es obligatoria")
    @DecimalMin(value = "0.0", inclusive = true, message = "La amortización no puede ser negativa")
    @Digits(integer = 10, fraction = 2, message = "La amortización debe tener hasta 2 decimales")
    @Column(name = "amortizacion", nullable = false, precision = 10, scale = 2)
    private BigDecimal amortizacion;

    @NotNull(message = "El monto de la cuota es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto de la cuota debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El monto de la cuota debe tener hasta 2 decimales")
    @Column(name = "monto_cuota", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoCuota;

    @NotNull(message = "El saldo final es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo final no puede ser negativo")
    @Digits(integer = 15, fraction = 2, message = "El saldo final debe tener hasta 2 decimales")
    @Column(name = "saldo_final", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoFinal;

    @NotNull(message = "El tipo de período es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_periodo", nullable = false, length = 20)
    private TipoPeriodoGracia tipoPeriodoGracia; // NORMAL, GRACIA_TOTAL, GRACIA_PARCIAL

    // Relación inversa hacia Crédito
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credito_id", nullable = false)
    private Credito credito;
}