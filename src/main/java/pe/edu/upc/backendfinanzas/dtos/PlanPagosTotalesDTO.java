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
public class PlanPagosTotalesDTO {
    private BigDecimal totalIntereses;
    private BigDecimal totalAmortizacionCapital;
    private BigDecimal totalSeguroDesgravamen;
    private BigDecimal totalSeguroRiesgo;
    private BigDecimal totalComisionPeriodica;
    private BigDecimal totalPortesGastosAdm;
}