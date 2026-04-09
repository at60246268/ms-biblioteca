package pe.edu.idat.biblioteca.dto.response;

public record RolResponseDto(
        Long idRol,
        String nombre,
        String descripcion,
        Integer estado
) {}

