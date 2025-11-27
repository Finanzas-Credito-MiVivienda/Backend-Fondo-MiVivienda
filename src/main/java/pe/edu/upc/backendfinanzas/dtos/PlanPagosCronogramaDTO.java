package pe.edu.upc.backendfinanzas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanPagosCronogramaDTO {
    private int numeroCuota;
    private BigDecimal tem;
    private BigDecimal ia;
    private BigDecimal ip;
    private String tipoPeriodo;
    private BigDecimal saldoInicial;
    private BigDecimal interes;
    private BigDecimal amortizacion;
    private BigDecimal cuota;
    private BigDecimal seguroDesgravamen;
    private BigDecimal seguroRiesgo;
    private BigDecimal comision;
    private BigDecimal portes;
    private BigDecimal gastosAdministracion;
    private BigDecimal saldoFinal;
    private BigDecimal flujo;
}