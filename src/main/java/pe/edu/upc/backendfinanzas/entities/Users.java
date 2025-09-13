package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "Users")
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true)
    private String username;
    @Column(length = 200)
    private String password;

    private Boolean enabled;

    @Column(name = "correo", nullable = false, length = 50)
    private String correo;
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;
    @Column(name = "apellidos", nullable = false, length = 150)
    private String apellidos;
    @Column(name = "estadocivil", nullable = false, length = 20)
    private String estadoCivil;
    @Column(name = "dni", nullable = false, length = 8)
    private String dni;
    @Column(name = "ingresosmensuales")
    private Double ingresosMensuales;
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;


   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "user_id")
   private List<Role> roles;

    //@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Credito> creditos;
}
