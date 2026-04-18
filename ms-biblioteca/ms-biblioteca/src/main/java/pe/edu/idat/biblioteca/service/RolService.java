package pe.edu.idat.biblioteca.service;

import pe.edu.idat.biblioteca.dto.request.RolRequestDto;
import pe.edu.idat.biblioteca.dto.response.RolResponseDto;

import java.util.List;

public interface RolService
{
    List<RolResponseDto> findAll();
    RolResponseDto findById(Long idRol);
    RolResponseDto create(RolRequestDto dto);
    RolResponseDto update(Long idRol, RolRequestDto dto);
    void delete(Long idRol);
}

