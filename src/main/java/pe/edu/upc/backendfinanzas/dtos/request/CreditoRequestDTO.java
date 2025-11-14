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
    // Atributos de Credito
    private TipoTasaInteres tipoTasaInteres;
    private BigDecimal tasaInteres;
    private FrecuenciaPago frecuenciaPago;
    private LocalDate fechaInicio;
    private TipoPeriodoGracia tipoPeriodoGracia;
    private int periodoGracia;
    private BigDecimal pCuotalnicial;
    private int numeroAnios;
    private BigDecimal cok; //preguntar si va en el modelo de base de datos

    // Atributo de Usuario
    private int idUsuario;
    // Atributo de Entidad
    private int idEntidadFinanciera;
    // Atributo de Inmueble
    private int idInmueble;
}