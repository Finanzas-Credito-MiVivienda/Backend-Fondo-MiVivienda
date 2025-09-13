package pe.edu.upc.backendfinanzas.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "rol"})})
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rol",nullable = false, length = 30)
    private String rol;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

}
