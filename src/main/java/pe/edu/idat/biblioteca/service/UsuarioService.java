package pe.edu.idat.biblioteca.service;

import pe.edu.idat.biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.biblioteca.dto.response.UsuarioResponseDto;

import java.util.List;

public interface UsuarioService
{
    List<UsuarioResponseDto> findAll();
    UsuarioResponseDto findById(Long idUsuario);
    UsuarioResponseDto create(UsuarioRequestDto dto);
    UsuarioResponseDto update(Long idUsuario, UsuarioRequestDto dto);
    void delete(Long idUsuario);
}

