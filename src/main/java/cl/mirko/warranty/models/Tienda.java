package cl.mirko.warranty.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "tienda",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_tienda_rut", columnNames = "rut_tienda")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tienda")
    private Integer idTienda;

    @Column(name = "nombre_tienda", nullable = false, length = 100)
    private String nombreTienda;

    @Column(name = "rut_tienda", nullable = false, length = 12)
    private String rutTienda;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;
}
