package pe.edu.idat.biblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolRequestDto
{
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 20, message = "El nombre del rol no debe superar los 20 caracteres")
    private String nombre;

    @Size(max = 100, message = "La descripción no debe superar los 100 caracteres")
    private String descripcion;
}

