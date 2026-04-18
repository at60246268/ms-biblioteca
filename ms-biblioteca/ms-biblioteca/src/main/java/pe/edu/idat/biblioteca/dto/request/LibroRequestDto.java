package pe.edu.idat.biblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroRequestDto
{
    @NotBlank(message = "El título del libro es obligatorio")
    @Size(max = 150, message = "El título no debe superar los 150 caracteres")
    private String titulo;

    @NotBlank(message = "El autor del libro es obligatorio")
    @Size(max = 100, message = "El autor no debe superar los 100 caracteres")
    private String autor;

    @NotBlank(message = "El ISBN es obligatorio")
    @Size(max = 17, message = "El ISBN no debe superar los 17 caracteres")
    private String isbn;

    @Size(max = 50, message = "El género no debe superar los 50 caracteres")
    private String genero;

    private Integer anioPublicacion;

    @NotNull(message = "La cantidad total de ejemplares es obligatoria")
    @Positive(message = "La cantidad total debe ser mayor a 0")
    private Integer cantidadTotal;
}

