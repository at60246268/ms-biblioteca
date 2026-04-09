package pe.edu.idat.biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.biblioteca.dto.response.ApiResponse;
import pe.edu.idat.biblioteca.dto.response.UsuarioResponseDto;
import pe.edu.idat.biblioteca.service.UsuarioService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController
{
    private final UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UsuarioResponseDto>>> findAll()
    {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.findAll()));
    }

    @GetMapping("/{idUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> findById(@PathVariable Long idUsuario)
    {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.findById(idUsuario)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> create(@Valid @RequestBody UsuarioRequestDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Usuario registrado exitosamente", usuarioService.create(dto)));
    }

    @PutMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> update(@PathVariable Long idUsuario,
                                                                  @Valid @RequestBody UsuarioRequestDto dto)
    {
        return ResponseEntity.ok(ApiResponse.ok("Usuario actualizado exitosamente", usuarioService.update(idUsuario, dto)));
    }

    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idUsuario)
    {
        usuarioService.delete(idUsuario);
        return ResponseEntity.ok(ApiResponse.ok("Usuario eliminado exitosamente", null));
    }
}

