package cl.mirko.warranty.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reclamo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reclamo")
    private Integer idReclamo;

    @Column(name = "fecha_reclamo", nullable = false)
    private LocalDate fechaReclamo;

    @Column(name = "resultado", nullable = false, length = 50)
    private String resultado;

    @ManyToOne
    @JoinColumn(name = "id_detalle", nullable = false)
    private DetalleBoleta detalle;
}
