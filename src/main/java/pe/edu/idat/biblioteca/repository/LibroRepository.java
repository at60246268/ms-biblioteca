package pe.edu.idat.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.idat.biblioteca.entity.Libro;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>
{
    List<Libro> findByEstado(Integer estado);

    @Query("SELECT l FROM Libro l WHERE UPPER(l.titulo) LIKE UPPER(CONCAT('%', :titulo, '%')) AND l.estado = :estado")
    List<Libro> findByTituloContainingIgnoreCaseAndEstado(@Param("titulo") String titulo, @Param("estado") Integer estado);

    @Query("SELECT l FROM Libro l WHERE UPPER(l.autor) LIKE UPPER(CONCAT('%', :autor, '%')) AND l.estado = :estado")
    List<Libro> findByAutorContainingIgnoreCaseAndEstado(@Param("autor") String autor, @Param("estado") Integer estado);
}

