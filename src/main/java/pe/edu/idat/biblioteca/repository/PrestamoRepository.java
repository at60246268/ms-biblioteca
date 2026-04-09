package pe.edu.idat.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.idat.biblioteca.entity.Prestamo;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long>
{
    List<Prestamo> findByUsuarioIdUsuario(Long idUsuario);
    List<Prestamo> findByLibroIdLibro(Long idLibro);
    List<Prestamo> findByEstado(Integer estado);
}

