package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "inmuebles")
public class Inmueble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String direccion;

    @Enumerated(EnumType.STRING)
    private TipoInmueble tipo; // Casa, Departamento, etc.
    private Double areaM2;
    private Double precioVenta;

    @Enumerated(EnumType.STRING)
    private EstadoInmueble estado; // DISPONIBLE, RESERVADO, VENDIDO

    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL)
    private List<Credito> creditos;
}
