package pe.edu.upc.backendfinanzas.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntidadFinancieraRequestDTO {
    private String nombreEntidad;
    private BigDecimal tea;
    private BigDecimal seguroDesgravamen;
    private BigDecimal seguroRiesgo;
    private BigDecimal gastosAdministracion;
    private BigDecimal costesRegistrales;
    private BigDecimal costesNotariales;
    private BigDecimal tasacion;
    private BigDecimal comisionEstudio;
    private BigDecimal comisionActivacion;
    private BigDecimal portes;
    private BigDecimal comisionPeriodica;
}