package pe.edu.idat.biblioteca.service;

import pe.edu.idat.biblioteca.dto.request.LibroRequestDto;
import pe.edu.idat.biblioteca.dto.response.LibroResponseDto;

import java.util.List;

public interface LibroService
{
    List<LibroResponseDto> findAll();
    LibroResponseDto findById(Long idLibro);
    LibroResponseDto create(LibroRequestDto dto);
    LibroResponseDto update(Long idLibro, LibroRequestDto dto);
    void delete(Long idLibro);
    List<LibroResponseDto> findByTitulo(String titulo);
    List<LibroResponseDto> findByAutor(String autor);
}

