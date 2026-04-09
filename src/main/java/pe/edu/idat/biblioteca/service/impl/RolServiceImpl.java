package pe.edu.idat.biblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.idat.biblioteca.dto.request.RolRequestDto;
import pe.edu.idat.biblioteca.dto.response.RolResponseDto;
import pe.edu.idat.biblioteca.entity.Rol;
import pe.edu.idat.biblioteca.mapper.RolMapper;
import pe.edu.idat.biblioteca.repository.RolRepository;
import pe.edu.idat.biblioteca.service.RolService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService
{
    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RolResponseDto> findAll()
    {
        return rolMapper.toResponseList(rolRepository.findByEstado(1));
    }

    @Override
    @Transactional(readOnly = true)
    public RolResponseDto findById(Long idRol)
    {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("No existe el rol con id: " + idRol));
        return rolMapper.toResponse(rol);
    }

    @Override
    @Transactional
    public RolResponseDto create(RolRequestDto dto)
    {
        log.info("Creando rol: {}", dto.getNombre());
        return rolMapper.toResponse(rolRepository.save(rolMapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public RolResponseDto update(Long idRol, RolRequestDto dto)
    {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("No existe el rol con id: " + idRol));
        rolMapper.updateFromRequest(dto, rol);
        log.info("Actualizando rol con id: {}", idRol);
        return rolMapper.toResponse(rolRepository.save(rol));
    }

    @Override
    @Transactional
    public void delete(Long idRol)
    {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("No existe el rol con id: " + idRol));
        rol.setEstado(0);
        rolRepository.save(rol);
        log.info("Rol con id {} eliminado lógicamente", idRol);
    }
}

