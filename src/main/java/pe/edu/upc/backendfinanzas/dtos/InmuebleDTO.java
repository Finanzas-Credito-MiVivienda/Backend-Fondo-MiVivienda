package pe.edu.upc.backendfinanzas.dtos;

import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;

import java.math.BigDecimal;
import java.util.List;

public class InmuebleDTO {

    private int id;
    private BigDecimal areaM2;
    private BigDecimal precioVenta;
    private String departamento;
    private String direccion;
    private TipoVivienda tipoDeVivienda;  // CASA, DEPARTAMENTO, TERRENO
    private String provincia;
    private String distrito;
    private List<Credito> creditos;

    public InmuebleDTO(int id, BigDecimal areaM2, BigDecimal precioVenta, String departamento, String direccion, TipoVivienda tipoDeVivienda, String provincia, String distrito, List<Credito> creditos) {
        this.id = id;
        this.areaM2 = areaM2;
        this.precioVenta = precioVenta;
        this.departamento = departamento;
        this.direccion = direccion;
        this.tipoDeVivienda = tipoDeVivienda;
        this.provincia = provincia;
        this.distrito = distrito;
        this.creditos = creditos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(BigDecimal areaM2) {
        this.areaM2 = areaM2;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoVivienda getTipoDeVivienda() {
        return tipoDeVivienda;
    }

    public void setTipoDeVivienda(TipoVivienda tipoDeVivienda) {
        this.tipoDeVivienda = tipoDeVivienda;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public List<Credito> getCreditos() {
        return creditos;
    }

    public void setCreditos(List<Credito> creditos) {
        this.creditos = creditos;
    }
}