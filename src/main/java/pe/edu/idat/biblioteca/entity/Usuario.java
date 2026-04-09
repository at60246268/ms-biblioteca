package pe.edu.idat.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tm_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tm_usuario")
    @SequenceGenerator(name = "seq_tm_usuario", sequenceName = "seq_tm_usuario", allocationSize = 1)
    @Column(name = "nid_usuario")
    private Long idUsuario;

    @Column(name = "susername", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "spassword", nullable = false)
    private String password;

    @Column(name = "snombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "sapellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "semail", unique = true, length = 100)
    private String email;

    @Column(name = "nestado", nullable = false)
    private Integer estado;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tm_usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    @Builder.Default
    private Set<Rol> roles = new HashSet<>();

    @PrePersist
    public void prePersist()
    {
        if (this.estado == null)
        {
            this.estado = 1;
        }
    }
}

