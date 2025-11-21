package cl.mirko.warranty.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "producto",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_producto_sku", columnNames = "sku")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre_producto", nullable = false, length = 150)
    private String nombreProducto;

    @Column(name = "marca", length = 80)
    private String marca;

    @Column(name = "modelo", length = 80)
    private String modelo;

    @Column(name = "categoria", length = 80)
    private String categoria;

    @Column(name = "sku", length = 60)
    private String sku;

    @Column(name = "garantia_meses", nullable = false)
    private Integer garantiaMeses;
}
