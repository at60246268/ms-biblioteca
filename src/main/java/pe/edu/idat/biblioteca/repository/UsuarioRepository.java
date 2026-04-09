package pe.edu.idat.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.idat.biblioteca.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{
    List<Usuario> findByEstado(Integer estado);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByUsernameAndEstado(String username, Integer estado);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

