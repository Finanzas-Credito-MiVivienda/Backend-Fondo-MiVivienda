package pe.edu.upc.backendfinanzas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanPagosCronogramaDTO {
    private int id;
    private int numeroCuota;
    private LocalDate fechaPago;
    private BigDecimal saldoInicial;
    private BigDecimal interes;
    private BigDecimal amortizacion;
    private BigDecimal montoCuota;
    private BigDecimal saldoFinal;
    private String tipoPeriodo; //Cambiar al tipo de perido porque uso enum
    private int creditoId;
}