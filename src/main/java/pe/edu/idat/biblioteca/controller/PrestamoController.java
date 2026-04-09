package pe.edu.idat.biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.biblioteca.dto.request.PrestamoRequestDto;
import pe.edu.idat.biblioteca.dto.response.ApiResponse;
import pe.edu.idat.biblioteca.dto.response.PrestamoResponseDto;
import pe.edu.idat.biblioteca.service.PrestamoService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/prestamos")
public class PrestamoController
{
    private final PrestamoService prestamoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PrestamoResponseDto>>> findAll()
    {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findAll()));
    }

    @GetMapping("/{idPrestamo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<ApiResponse<PrestamoResponseDto>> findById(@PathVariable Long idPrestamo)
    {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findById(idPrestamo)));
    }

    @GetMapping("/historial/{idUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<ApiResponse<List<PrestamoResponseDto>>> historialPorUsuario(@PathVariable Long idUsuario)
    {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findByUsuario(idUsuario)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PrestamoResponseDto>> create(@Valid @RequestBody PrestamoRequestDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Préstamo registrado exitosamente", prestamoService.create(dto)));
    }

    @PutMapping("/{idPrestamo}/devolucion")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PrestamoResponseDto>> registrarDevolucion(@PathVariable Long idPrestamo)
    {
        return ResponseEntity.ok(ApiResponse.ok("Devolución registrada exitosamente",
                prestamoService.registrarDevolucion(idPrestamo)));
    }
}

