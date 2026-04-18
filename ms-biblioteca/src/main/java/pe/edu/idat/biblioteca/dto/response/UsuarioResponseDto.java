package pe.edu.idat.biblioteca.dto.response;

import java.util.List;

public record UsuarioResponseDto(
        Long idUsuario,
        String username,
        String nombres,
        String apellidos,
        String email,
        Integer estado,
        List<String> roles
) {}

