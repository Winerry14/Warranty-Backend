package cl.mirko.warranty.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "regla_notificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReglaNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_regla")
    private Integer idRegla;

    @Column(name = "nombre_regla", nullable = false, length = 120)
    private String nombreRegla;

    @Column(name = "dias_previstos", nullable = false)
    private Integer diasPrevistos;

    @Column(name = "canal", nullable = false, length = 50)
    private String canal;

    @Lob
    @Column(name = "plantilla", nullable = false)
    private String plantilla;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
