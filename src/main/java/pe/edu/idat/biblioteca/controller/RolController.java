package pe.edu.idat.biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.biblioteca.dto.request.RolRequestDto;
import pe.edu.idat.biblioteca.dto.response.ApiResponse;
import pe.edu.idat.biblioteca.dto.response.RolResponseDto;
import pe.edu.idat.biblioteca.service.RolService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RolController
{
    private final RolService rolService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<RolResponseDto>>> findAll()
    {
        return ResponseEntity.ok(ApiResponse.ok(rolService.findAll()));
    }

    @GetMapping("/{idRol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RolResponseDto>> findById(@PathVariable Long idRol)
    {
        return ResponseEntity.ok(ApiResponse.ok(rolService.findById(idRol)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RolResponseDto>> create(@Valid @RequestBody RolRequestDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Rol creado exitosamente", rolService.create(dto)));
    }

    @PutMapping("/{idRol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RolResponseDto>> update(@PathVariable Long idRol,
                                                              @Valid @RequestBody RolRequestDto dto)
    {
        return ResponseEntity.ok(ApiResponse.ok("Rol actualizado exitosamente", rolService.update(idRol, dto)));
    }

    @DeleteMapping("/{idRol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idRol)
    {
        rolService.delete(idRol);
        return ResponseEntity.ok(ApiResponse.ok("Rol eliminado exitosamente", null));
    }
}

