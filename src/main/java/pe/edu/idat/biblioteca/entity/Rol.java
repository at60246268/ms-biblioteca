package pe.edu.idat.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tm_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tm_rol")
    @SequenceGenerator(name = "seq_tm_rol", sequenceName = "seq_tm_rol", allocationSize = 1)
    @Column(name = "nid_rol")
    private Long idRol;

    @Column(name = "snombre", nullable = false, unique = true, length = 20)
    private String nombre;

    @Column(name = "sdescripcion", length = 100)
    private String descripcion;

    @Column(name = "nestado", nullable = false)
    private Integer estado;

    @PrePersist
    public void prePersist()
    {
        if (this.estado == null)
        {
            this.estado = 1;
        }
    }
}

