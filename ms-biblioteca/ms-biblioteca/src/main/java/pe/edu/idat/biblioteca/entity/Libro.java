package pe.edu.idat.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tm_libro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tm_libro")
    @SequenceGenerator(name = "seq_tm_libro", sequenceName = "seq_tm_libro", allocationSize = 1)
    @Column(name = "nid_libro")
    private Long idLibro;

    @Column(name = "stitulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "sautor", nullable = false, length = 100)
    private String autor;

    @Column(name = "sisbn", nullable = false, unique = true, length = 17)
    private String isbn;

    @Column(name = "sgenero", length = 50)
    private String genero;

    @Column(name = "nanio_publicacion")
    private Integer anioPublicacion;

    @Column(name = "ncantidad_total", nullable = false)
    private Integer cantidadTotal;

    @Column(name = "ncantidad_disponible", nullable = false)
    private Integer cantidadDisponible;

    @Column(name = "nestado", nullable = false)
    private Integer estado;

    @PrePersist
    public void prePersist()
    {
        if (this.estado == null)
        {
            this.estado = 1;
        }
        if (this.cantidadDisponible == null)
        {
            this.cantidadDisponible = this.cantidadTotal;
        }
    }
}

