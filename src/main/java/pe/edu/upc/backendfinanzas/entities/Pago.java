package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer numeroCuota;
    private LocalDate fechaPago;
    private Double saldoInicial;
    private Double interes;
    private Double amortizacion;
    private Double montoCuota;
    private Double saldoFinal;

    @Enumerated(EnumType.STRING)
    private TipoPeriodo tipoPeriodo; // NORMAL, GRACIA_TOTAL, GRACIA_PARCIAL

    // Relación inversa hacia Crédito
    @ManyToOne
    @JoinColumn(name = "credito_id")
    private Credito credito;
}
