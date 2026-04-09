package pe.edu.idat.biblioteca.dto.response;

import java.time.LocalDate;

public record PrestamoResponseDto(
        Long idPrestamo,
        Long idUsuario,
        String usernameUsuario,
        Long idLibro,
        String tituloLibro,
        LocalDate fechaPrestamo,
        LocalDate fechaDevolucionEsperada,
        LocalDate fechaDevolucionReal,
        Integer estado
) {}

