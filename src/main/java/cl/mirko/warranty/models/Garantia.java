package cl.mirko.warranty.models;

import java.time.LocalDate;
import java.util.List; 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; 

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "garantia",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_garantia_boleta", columnNames = "id_boleta")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Garantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_garantia")
    private Integer idGarantia;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_termino", nullable = false)
    private LocalDate fechaTermino;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @OneToOne
    @JoinColumn(name = "id_boleta", nullable = false)
    
    @JsonIgnoreProperties("garantia") 
    private Boleta boleta;

    
    @OneToMany(mappedBy = "garantia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("garantia") 
    private List<Notificacion> notificaciones;
}