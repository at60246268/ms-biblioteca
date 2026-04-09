package pe.edu.idat.biblioteca.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoRequestDto
{
    @NotNull(message = "El id del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El id del libro es obligatorio")
    private Long idLibro;

    @NotNull(message = "La fecha de devolución esperada es obligatoria")
    @Future(message = "La fecha de devolución debe ser una fecha futura")
    private LocalDate fechaDevolucionEsperada;
}

