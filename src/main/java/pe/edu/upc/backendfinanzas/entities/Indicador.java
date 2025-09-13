package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "indicadores")
public class Indicador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double valorActual;  // Valor Actual Neto
    private Double tasaInternaRetorno;  // Tasa Interna de Retorno
    private Double costoEfectivoTotal;
    private Double tasaEfectiva;  // Tasa Efectiva Anual
    private Double tasaEfectivaMensual;  // Tasa Efectiva Mensual
    private Double costoTotalCredito; // CTC

    // Relación inversa hacia Crédito
    @OneToOne
    @JoinColumn(name = "credito_id", nullable = false)
    private Credito credito;
}
