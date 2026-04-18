package pe.edu.idat.biblioteca.dto.response;

public record LibroResponseDto(
        Long idLibro,
        String titulo,
        String autor,
        String isbn,
        String genero,
        Integer anioPublicacion,
        Integer cantidadTotal,
        Integer cantidadDisponible,
        Integer estado
) {}

