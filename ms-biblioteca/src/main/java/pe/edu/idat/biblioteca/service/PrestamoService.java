package pe.edu.idat.biblioteca.service;

import pe.edu.idat.biblioteca.dto.request.PrestamoRequestDto;
import pe.edu.idat.biblioteca.dto.response.PrestamoResponseDto;

import java.util.List;

public interface PrestamoService
{
    List<PrestamoResponseDto> findAll();
    PrestamoResponseDto findById(Long idPrestamo);
    PrestamoResponseDto create(PrestamoRequestDto dto);
    PrestamoResponseDto registrarDevolucion(Long idPrestamo);
    List<PrestamoResponseDto> findByUsuario(Long idUsuario);
}

