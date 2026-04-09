package pe.edu.idat.biblioteca.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequestDto
{
    @NotBlank(message = "El username es obligatorio")
    @Size(max = 50, message = "El username no debe superar los 50 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no deben superar los 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no deben superar los 100 caracteres")
    private String apellidos;

    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no debe superar los 100 caracteres")
    private String email;

    private List<Long> idRoles;
}

