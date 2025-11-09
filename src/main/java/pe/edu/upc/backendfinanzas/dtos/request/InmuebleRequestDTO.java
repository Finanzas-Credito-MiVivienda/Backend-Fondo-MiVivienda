package pe.edu.upc.backendfinanzas.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.backendfinanzas.entities.EstadoVivienda;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InmuebleRequestDTO {
    private BigDecimal areaM2;
    private BigDecimal precioVenta;
    private String departamento;
    private String direccion;
    private EstadoVivienda estadoVivienda;
    private TipoVivienda tipoVivienda;
    private String provincia;
    private String distrito;
}