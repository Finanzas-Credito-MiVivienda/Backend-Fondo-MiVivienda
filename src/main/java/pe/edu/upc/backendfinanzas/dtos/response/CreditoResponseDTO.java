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
    private FrecuenciaPago frecuenciaPago;
    private LocalDate fechaInicio;
    private BigDecimal bonoBuenPagador;
    private TipoPeriodoGracia tipoPeriodoGracia; //falta
    private int periodoGracia; //falta
    private BigDecimal pCuotalnicial;
    private int numeroAnios;
    private int numeroDiasxAnio; //Poner dato automatioc = 360
    private int nCuotasxAnio;
    private int nTotalCuotas;
    private BigDecimal seguroDegPerd;
    private BigDecimal seguroRiesgoPerd;

    private BigDecimal tasaDescuento; // Atributo de Entitie INDICADOR

    // Atributo de Usuario
    private int idUsuario;
    // Atributo de Entidad
    private int idEntidadFinanciera;
    // Atributo de Inmueble
    private int idInmueble;

    //PREGUNTAR SI VA ESTOS ATRIBUTOS
    //private String nombreUsuario;
    //private String direccionInmueble;
}