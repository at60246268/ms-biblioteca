package pe.edu.idat.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tm_prestamo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prestamo
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tm_prestamo")
    @SequenceGenerator(name = "seq_tm_prestamo", sequenceName = "seq_tm_prestamo", allocationSize = 1)
    @Column(name = "nid_prestamo")
    private Long idPrestamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    @Column(name = "dfecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "dfecha_devolucion_esperada", nullable = false)
    private LocalDate fechaDevolucionEsperada;

    @Column(name = "dfecha_devolucion_real")
    private LocalDate fechaDevolucionReal;

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

