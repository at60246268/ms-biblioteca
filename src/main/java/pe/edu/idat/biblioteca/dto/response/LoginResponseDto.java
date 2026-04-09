package pe.edu.idat.biblioteca.dto.response;

import java.util.List;

public record LoginResponseDto(
        String token,
        String username,
        List<String> roles
) {}

