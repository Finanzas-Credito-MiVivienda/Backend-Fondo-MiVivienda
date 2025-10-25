package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creditos")
public class Credito {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    // Monto Total prestado
    @NotNull(message = "El monto prestado es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que 0")
    @Column(name = "monto_prestado", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoPrestamo;

    // Moneda del credito
    @NotNull(message = "La moneda es obligatoria")
    @Enumerated(EnumType.STRING) //Puede usar solo los valores establecidos en la declaración de su enum
    @Column(name = "moneda", nullable = false, length = 10)
    private Moneda moneda; // PEN, USD

    // Tipo de tasa
    @NotNull(message = "El tipo de tasa es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tasa", nullable = false, length = 15)
    private TipoTasaInteres tipoTasaInteres; // EFECTIVA, NOMINAL

    //Valor de la tasa de interes
    @NotNull(message = "La tasa de interés es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La tasa debe ser mayor que 0")
    @Column(name = "tasa_interes", nullable = false, precision = 5, scale = 2)
    private BigDecimal tasaInteres;

    // Frecuencia de capitalizacion
    @NotNull(message = "La capitalización es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(name = "capitalizacion", nullable = false, length = 15)
    private Capitalizacion capitalizacion; // MENSUAL, DIARIA (si es nominal)

    // Tipo de Plazo
    @NotNull(message = "El tipo de plazo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_plazo", nullable = false, length = 15)
    private TipoPlazo tipoPlazo; // DIAS, MESES, ANIOS

    // Plazo
    @NotNull(message = "El plazo es obligatorio")
    @Column(name = "plazo", nullable = false)
    private int plazo;

    // Frecuencia de pago
    @NotNull(message = "La frecuencia de pago es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(name = "frecuencia_pago", nullable = false, length = 15)
    private FrecuenciaPago frecuenciaPago; // MENSUAL, SEMESTRAL, ANUAL

    // Fecha inicio del credito
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    // Bono del Buen Pagador
    @NotNull(message = "El Bono del Buen Pagador es obligatorio para créditos MiVivienda")
    @DecimalMin(value = "0.0", inclusive = false, message = "El Bono debe ser mayor que 0")
    @Column(name = "bono_buen_pagador", nullable = false, precision = 10, scale = 2)
    private BigDecimal bonoBuenPagador;

    // Tipo Periodo de Gracia
    @NotNull(message = "El tipo periodo de gracia es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_periodo_gracia", nullable = false, length = 15)
    private TipoPeriodoGracia tipoPeriodoGracia; // NINGUNO, TOTAL, PARCIAL

    // Periodo de Gracia
    @NotNull(message = "El periodo de gracia es obligatorio")
    @Column(name = "periodo_gracia", nullable = false)
    private int periodoGracia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users usuario;

    // Relación con Inmueble
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inmueble_id", nullable = false)
    private Inmueble inmueble;

    // Relación con Pagos (una cuota por cada registro)
    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;

    // Relación con Entidad Financiera
    @OneToOne(mappedBy = "credito", cascade = CascadeType.ALL, orphanRemoval = true)
    private EntidadFinanciera entidadFinanciera;
}