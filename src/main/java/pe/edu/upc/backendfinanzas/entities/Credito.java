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

    @NotNull(message = "El monto prestado es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que 0")
    @Column(name = "monto_prestado", nullable = true, precision = 15, scale = 2)
    private BigDecimal montoPrestamo;

    @NotNull(message = "El saldo financiar es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El saldo financiar debe ser mayor que 0")
    @Column(name = "saldo_financiar", nullable = true, precision = 15, scale = 2)
    private BigDecimal saldoFinanciar;

    @NotNull(message = "El tipo de tasa es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tasa", nullable = true, length = 15)
    private TipoTasaInteres tipoTasaInteres;

    @NotNull(message = "La tasa de interés es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La tasa debe ser mayor que 0")
    @Column(name = "tasa_interes", nullable = true, precision = 12, scale = 7)
    private BigDecimal tasaInteres;

    @NotNull(message = "La frecuencia de pago es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(name = "frecuencia_pago", nullable = true, length = 15)
    private FrecuenciaPago frecuenciaPago;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = true)
    private LocalDate fechaInicio;

    @NotNull(message = "El Bono del Buen Pagador es obligatorio para créditos MiVivienda")
    @DecimalMin(value = "0.0", inclusive = false, message = "El Bono debe ser mayor que 0")
    @Column(name = "bono_buen_pagador", nullable = true, precision = 10, scale = 2)
    private BigDecimal bonoBuenPagador;

    @NotNull(message = "El tipo periodo de gracia es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_periodo_gracia", nullable = true, length = 15)
    private TipoPeriodoGracia tipoPeriodoGracia;

    @NotNull(message = "El periodo de gracia es obligatorio")
    @Column(name = "periodo_gracia", nullable = true)
    private int periodoGracia;

    @DecimalMin(value = "0.0", inclusive = true, message = "El porcentaje debe ser mayor o igual que 0")
    @Column(name = "p_cuota_inicial", precision = 5, scale = 3)
    private BigDecimal pCuotaInicial;

    @Column(name = "numeroAnios")
    private int numeroAnios;

    @Column(name = "numeroDiasxAnio")
    private int numeroDiasxAnio;

    @Column(name = "nCuotasxAnio")
    private int nCuotasxAnio;

    @Column(name = "nTotalCuotas")
    private int nTotalCuotas;

    @DecimalMin(value = "0.0", inclusive = true, message = "El seguro debe ser mayor o igual que 0")
    @Column(name = "seguroDegPerd", precision = 10, scale = 5)
    private BigDecimal seguroDegPerd;

    @DecimalMin(value = "0.0", inclusive = true, message = "El seguro debe ser mayor o igual que 0")
    @Column(name = "seguroRiesgoPerd", precision = 10, scale = 5)
    private BigDecimal seguroRiesgoPerd;

    @DecimalMin(value = "0.0", inclusive = true, message = "El cok debe ser mayor o igual que 0")
    @Column(name = "cok", precision = 10, scale = 5)
    private BigDecimal cok;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private Users usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inmueble_id", nullable = true)
    private Inmueble inmueble;

    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidad_financiera_id", nullable = true)
    private EntidadFinanciera entidadFinanciera;
}