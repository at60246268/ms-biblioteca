package pe.edu.idat.biblioteca.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.idat.biblioteca.dto.request.LibroRequestDto;
import pe.edu.idat.biblioteca.dto.response.LibroResponseDto;
import pe.edu.idat.biblioteca.entity.Libro;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T17:59:21-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class LibroMapperImpl implements LibroMapper {

    @Override
    public Libro toEntity(LibroRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Libro.LibroBuilder libro = Libro.builder();

        libro.titulo( dto.getTitulo() );
        libro.autor( dto.getAutor() );
        libro.isbn( dto.getIsbn() );
        libro.genero( dto.getGenero() );
        libro.anioPublicacion( dto.getAnioPublicacion() );
        libro.cantidadTotal( dto.getCantidadTotal() );

        return libro.build();
    }

    @Override
    public LibroResponseDto toResponse(Libro libro) {
        if ( libro == null ) {
            return null;
        }

        Long idLibro = null;
        String titulo = null;
        String autor = null;
        String isbn = null;
        String genero = null;
        Integer anioPublicacion = null;
        Integer cantidadTotal = null;
        Integer cantidadDisponible = null;
        Integer estado = null;

        idLibro = libro.getIdLibro();
        titulo = libro.getTitulo();
        autor = libro.getAutor();
        isbn = libro.getIsbn();
        genero = libro.getGenero();
        anioPublicacion = libro.getAnioPublicacion();
        cantidadTotal = libro.getCantidadTotal();
        cantidadDisponible = libro.getCantidadDisponible();
        estado = libro.getEstado();

        LibroResponseDto libroResponseDto = new LibroResponseDto( idLibro, titulo, autor, isbn, genero, anioPublicacion, cantidadTotal, cantidadDisponible, estado );

        return libroResponseDto;
    }

    @Override
    public void updateFromRequest(LibroRequestDto dto, Libro libro) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitulo() != null ) {
            libro.setTitulo( dto.getTitulo() );
        }
        if ( dto.getAutor() != null ) {
            libro.setAutor( dto.getAutor() );
        }
        if ( dto.getIsbn() != null ) {
            libro.setIsbn( dto.getIsbn() );
        }
        if ( dto.getGenero() != null ) {
            libro.setGenero( dto.getGenero() );
        }
        if ( dto.getAnioPublicacion() != null ) {
            libro.setAnioPublicacion( dto.getAnioPublicacion() );
        }
        if ( dto.getCantidadTotal() != null ) {
            libro.setCantidadTotal( dto.getCantidadTotal() );
        }
    }

    @Override
    public List<LibroResponseDto> toResponseList(List<Libro> libros) {
        if ( libros == null ) {
            return null;
        }

        List<LibroResponseDto> list = new ArrayList<LibroResponseDto>( libros.size() );
        for ( Libro libro : libros ) {
            list.add( toResponse( libro ) );
        }

        return list;
    }
}
