package pe.edu.upc.backendfinanzas.dtos;

import pe.edu.upc.backendfinanzas.entities.Credito;
import pe.edu.upc.backendfinanzas.entities.TipoVivienda;

import java.util.List;

public class InmuebleDTO {
    private int id;
    private String direccion;
    private TipoVivienda tipo; // Casa, Departamento, etc.
    private Double areaM2;
    private Double precioVenta;
    //private EstadoInmueble estado; // DISPONIBLE, RESERVADO, VENDIDO
    private List<Credito> creditos;
}