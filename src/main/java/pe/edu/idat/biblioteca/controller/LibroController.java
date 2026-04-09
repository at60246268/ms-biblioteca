package pe.edu.idat.biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.biblioteca.dto.request.LibroRequestDto;
import pe.edu.idat.biblioteca.dto.response.ApiResponse;
import pe.edu.idat.biblioteca.dto.response.LibroResponseDto;
import pe.edu.idat.biblioteca.service.LibroService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/libros")
public class LibroController
{
    private final LibroService libroService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<LibroResponseDto>>> findAll()
    {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findAll()));
    }

    @GetMapping("/{idLibro}")
    public ResponseEntity<ApiResponse<LibroResponseDto>> findById(@PathVariable Long idLibro)
    {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findById(idLibro)));
    }

    @GetMapping("/buscar/titulo")
    public ResponseEntity<ApiResponse<List<LibroResponseDto>>> findByTitulo(@RequestParam String titulo)
    {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findByTitulo(titulo)));
    }

    @GetMapping("/buscar/autor")
    public ResponseEntity<ApiResponse<List<LibroResponseDto>>> findByAutor(@RequestParam String autor)
    {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findByAutor(autor)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LibroResponseDto>> create(@Valid @RequestBody LibroRequestDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Libro registrado exitosamente", libroService.create(dto)));
    }

    @PutMapping("/{idLibro}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LibroResponseDto>> update(@PathVariable Long idLibro,
                                                                @Valid @RequestBody LibroRequestDto dto)
    {
        return ResponseEntity.ok(ApiResponse.ok("Libro actualizado exitosamente", libroService.update(idLibro, dto)));
    }

    @DeleteMapping("/{idLibro}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idLibro)
    {
        libroService.delete(idLibro);
        return ResponseEntity.ok(ApiResponse.ok("Libro eliminado exitosamente", null));
    }
}

