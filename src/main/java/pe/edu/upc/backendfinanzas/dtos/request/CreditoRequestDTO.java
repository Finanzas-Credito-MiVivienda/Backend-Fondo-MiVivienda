package pe.edu.upc.backendfinanzas.dtos.request;

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
public class CreditoRequestDTO {
    private TipoTasaInteres tipoTasaInteres;
    private BigDecimal tasaInteres;
    private FrecuenciaPago frecuenciaPago;
    private LocalDate fechaInicio;
    private TipoPeriodoGracia tipoPeriodoGracia;
    private int periodoGracia;
    private BigDecimal pCuotaInicial;
    private int numeroAnios;
    private BigDecimal cok;
    private int idUsuario;
    private int idEntidadFinanciera;
    private int idInmueble;
}