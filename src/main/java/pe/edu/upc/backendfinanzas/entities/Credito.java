package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "creditos")
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double montoPrestamo;

    @Enumerated(EnumType.STRING)//puede usar solo los valores establecidos en la declaración de su enum
    private Moneda moneda; // PEN, USD

    @Enumerated(EnumType.STRING)
    private TipoTasa tipoTasa; // EFECTIVA, NOMINAL
    private Double tasaInteres;

    @Enumerated(EnumType.STRING)
    private Capitalizacion capitalizacion; // MENSUAL, DIARIA (si es nominal)
    private Integer plazoMeses;

    @Enumerated(EnumType.STRING)
    private FrecuenciaPago frecuenciaPago; // MENSUAL, SEMESTRAL, ANUAL

    @Enumerated(EnumType.STRING)
    private PeriodoGracia periodoGracia; // NINGUNO, TOTAL, PARCIAL
    private LocalDate fechaInicio;
    private Double bonoBuenPagador;
    private Integer plazoAnios;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users cliente;

    // Relación con Inmueble
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;

    // Relación con Pagos (una cuota por cada registro)
    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;

    // Relación con Indicadores
    @OneToOne(mappedBy = "credito", cascade = CascadeType.ALL, orphanRemoval = true)
    private Indicador indicador;
}
