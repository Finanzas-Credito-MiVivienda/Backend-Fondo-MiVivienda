package pe.edu.upc.backendfinanzas.dtos.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.backendfinanzas.entities.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditoResponseDTO {
    private int id;
    private BigDecimal saldoFinanciar;
    private BigDecimal montoPrestamo;
    private int nCuotasxAnio;
    private int nTotalCuotas;
    private BigDecimal seguroDegPerd;
    private BigDecimal seguroRiesgoPerd;
    private BigDecimal tasaDescuento;
    private TipoTasaInteres tipoTasaInteres;
    private BigDecimal tasaInteres;
    private FrecuenciaPago frecuenciaPago;
    private LocalDate fechaInicio;
    private BigDecimal bonoBuenPagador;
    private TipoPeriodoGracia tipoPeriodoGracia;
    private int periodoGracia;
    private String nombreUsuario;
    private String direccionInmueble;
}