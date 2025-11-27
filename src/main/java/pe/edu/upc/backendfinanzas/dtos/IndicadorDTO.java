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
public class IndicadorDTO {
    private BigDecimal tasaDescuento;
    private BigDecimal tirPeriodo;
    private BigDecimal tceaOperacion;
    private BigDecimal van;
}