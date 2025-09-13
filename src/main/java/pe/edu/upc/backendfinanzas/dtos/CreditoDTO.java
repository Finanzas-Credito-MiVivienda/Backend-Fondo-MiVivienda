package pe.edu.upc.backendfinanzas.dtos;

import pe.edu.upc.backendfinanzas.entities.*;

import java.time.LocalDate;
import java.util.List;

public class CreditoDTO {
    private int id;
    private Double montoPrestamo;
    private Moneda moneda; // PEN, USD
    private TipoTasa tipoTasa; // EFECTIVA, NOMINAL
    private Double tasaInteres;
    private Capitalizacion capitalizacion; // MENSUAL, DIARIA (si es nominal)
    private Integer plazoMeses;
    private FrecuenciaPago frecuenciaPago; // MENSUAL, SEMESTRAL, ANUAL
    private PeriodoGracia periodoGracia; // NINGUNO, TOTAL, PARCIAL
    private LocalDate fechaInicio;
    private Double bonoBuenPagador;
    private Integer plazoAnios;
    private Inmueble inmueble;
    private List<Pago> pagos;
    private Indicador indicador;
    private Users cliente;

}