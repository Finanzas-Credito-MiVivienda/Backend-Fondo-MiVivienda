package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
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

    @Digits(integer = 15, fraction = 2, message = "El Valor Actual debe tener hasta 2 decimales")
    @Column(name = "valor_actual_neto", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorActualNeto;

    @DecimalMin(value = "0.0", inclusive = false, message = "La TIR debe ser mayor que 0")
    @Column(name = "tasa_interna_retorno", nullable = false, precision = 7, scale = 4)
    private BigDecimal tasaInternaRetorno;

    @DecimalMin(value = "0.0", inclusive = false, message = "La TCEA debe ser mayor que 0")
    @Column(name = "teca_operacion", nullable = false, precision = 7, scale = 4)
    private BigDecimal tceaOperacion;

    @DecimalMin(value = "0.0", inclusive = false, message = "La tasa de descuento debe ser mayor que 0")
    @Column(name = "tasa_descuento", nullable = false, precision = 7, scale = 4)
    private BigDecimal tasaDescuento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pago_id", nullable = false, unique = true)
    private Pago pago;
}