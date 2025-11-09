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
    // Atributos de Entidad
    private int idEntidadFinanciera;
    // Atributos de Credito
    private TipoTasaInteres tipoTasaInteres;
    private FrecuenciaPago frecuenciaPago;
    private BigDecimal montoPrestamo;
    private BigDecimal tasaInteres;
    private TipoPlazo tipoPlazo;
    private int numeroAnios;
    private BigDecimal cok;
    private LocalDate fechaInicio;
    private TipoPeriodoGracia tipoPeriodoGracia;
    private int periodoGracia;
    private int usuarioId;
    private BigDecimal pCuotalnicial;
    // Atributo de Inmueble
    private int idInmueble;
}