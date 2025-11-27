package pe.edu.upc.backendfinanzas.dtos.response;

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
    private BigDecimal montoPrestamo;
    private BigDecimal saldoFinanciar;
    private TipoTasaInteres tipoTasaInteres;
    private BigDecimal tasaInteres;
    private BigDecimal teaOriginal;
    private FrecuenciaPago frecuenciaPago;
    private LocalDate fechaInicio;
    private BigDecimal bonoBuenPagador;
    private TipoPeriodoGracia tipoPeriodoGracia;
    private int periodoGracia;
    private BigDecimal pCuotaInicial;
    private int numeroAnios;
    private int numeroDiasxAnio;
    private int nCuotasxAnio;
    private int nTotalCuotas;
    private BigDecimal seguroDegPerd;
    private BigDecimal seguroRiesgoPerd;
    private BigDecimal tasaDescuento;
    private int idUsuario;
    private int idEntidadFinanciera;
    private int idInmueble;
}