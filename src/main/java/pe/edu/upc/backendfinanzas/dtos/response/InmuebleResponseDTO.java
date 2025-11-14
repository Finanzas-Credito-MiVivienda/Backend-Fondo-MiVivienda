package pe.edu.upc.backendfinanzas.dtos.response;

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
public class InmuebleResponseDTO {
    private int id;
    private BigDecimal areaM2;
    private BigDecimal precioVenta;
    private String departamento;
    private String direccion;
    private TipoVivienda tipoVivienda;
    private String provincia;
    private String distrito;
    private EstadoVivienda estadoVivienda;
    private String imageUrl;
}