package pe.edu.idat.biblioteca.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.idat.biblioteca.dto.request.PrestamoRequestDto;
import pe.edu.idat.biblioteca.dto.response.PrestamoResponseDto;
import pe.edu.idat.biblioteca.entity.Libro;
import pe.edu.idat.biblioteca.entity.Prestamo;
import pe.edu.idat.biblioteca.entity.Usuario;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T17:59:21-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class PrestamoMapperImpl implements PrestamoMapper {

    @Override
    public Prestamo toEntity(PrestamoRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Prestamo.PrestamoBuilder prestamo = Prestamo.builder();

        prestamo.fechaDevolucionEsperada( dto.getFechaDevolucionEsperada() );

        return prestamo.build();
    }

    @Override
    public PrestamoResponseDto toResponse(Prestamo prestamo) {
        if ( prestamo == null ) {
            return null;
        }

        Long idUsuario = null;
        String usernameUsuario = null;
        Long idLibro = null;
        String tituloLibro = null;
        Long idPrestamo = null;
        LocalDate fechaPrestamo = null;
        LocalDate fechaDevolucionEsperada = null;
        LocalDate fechaDevolucionReal = null;
        Integer estado = null;

        idUsuario = prestamoUsuarioIdUsuario( prestamo );
        usernameUsuario = prestamoUsuarioUsername( prestamo );
        idLibro = prestamoLibroIdLibro( prestamo );
        tituloLibro = prestamoLibroTitulo( prestamo );
        idPrestamo = prestamo.getIdPrestamo();
        fechaPrestamo = prestamo.getFechaPrestamo();
        fechaDevolucionEsperada = prestamo.getFechaDevolucionEsperada();
        fechaDevolucionReal = prestamo.getFechaDevolucionReal();
        estado = prestamo.getEstado();

        PrestamoResponseDto prestamoResponseDto = new PrestamoResponseDto( idPrestamo, idUsuario, usernameUsuario, idLibro, tituloLibro, fechaPrestamo, fechaDevolucionEsperada, fechaDevolucionReal, estado );

        return prestamoResponseDto;
    }

    @Override
    public List<PrestamoResponseDto> toResponseList(List<Prestamo> prestamos) {
        if ( prestamos == null ) {
            return null;
        }

        List<PrestamoResponseDto> list = new ArrayList<PrestamoResponseDto>( prestamos.size() );
        for ( Prestamo prestamo : prestamos ) {
            list.add( toResponse( prestamo ) );
        }

        return list;
    }

    private Long prestamoUsuarioIdUsuario(Prestamo prestamo) {
        Usuario usuario = prestamo.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        return usuario.getIdUsuario();
    }

    private String prestamoUsuarioUsername(Prestamo prestamo) {
        Usuario usuario = prestamo.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        return usuario.getUsername();
    }

    private Long prestamoLibroIdLibro(Prestamo prestamo) {
        Libro libro = prestamo.getLibro();
        if ( libro == null ) {
            return null;
        }
        return libro.getIdLibro();
    }

    private String prestamoLibroTitulo(Prestamo prestamo) {
        Libro libro = prestamo.getLibro();
        if ( libro == null ) {
            return null;
        }
        return libro.getTitulo();
    }
}
