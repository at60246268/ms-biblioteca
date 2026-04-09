package pe.edu.idat.biblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.idat.biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.biblioteca.dto.response.UsuarioResponseDto;
import pe.edu.idat.biblioteca.entity.Rol;
import pe.edu.idat.biblioteca.entity.Usuario;
import pe.edu.idat.biblioteca.mapper.UsuarioMapper;
import pe.edu.idat.biblioteca.repository.RolRepository;
import pe.edu.idat.biblioteca.repository.UsuarioRepository;
import pe.edu.idat.biblioteca.service.UsuarioService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService
{
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> findAll()
    {
        return usuarioMapper.toResponseList(usuarioRepository.findByEstado(1));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDto findById(Long idUsuario)
    {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con id: " + idUsuario));
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDto create(UsuarioRequestDto dto)
    {
        if (usuarioRepository.existsByUsername(dto.getUsername()))
        {
            throw new RuntimeException("El username ya está en uso: " + dto.getUsername());
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getIdRoles() != null && !dto.getIdRoles().isEmpty())
        {
            Set<Rol> roles = new HashSet<>();
            for (Long idRol : dto.getIdRoles())
            {
                Rol rol = rolRepository.findById(idRol)
                        .orElseThrow(() -> new RuntimeException("No existe el rol con id: " + idRol));
                roles.add(rol);
            }
            usuario.setRoles(roles);
        }

        log.info("Registrando usuario: {}", dto.getUsername());
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioResponseDto update(Long idUsuario, UsuarioRequestDto dto)
    {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con id: " + idUsuario));

        usuarioMapper.updateFromRequest(dto, usuario);

        if (dto.getPassword() != null && !dto.getPassword().isBlank())
        {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getIdRoles() != null && !dto.getIdRoles().isEmpty())
        {
            Set<Rol> roles = new HashSet<>();
            for (Long idRol : dto.getIdRoles())
            {
                Rol rol = rolRepository.findById(idRol)
                        .orElseThrow(() -> new RuntimeException("No existe el rol con id: " + idRol));
                roles.add(rol);
            }
            usuario.setRoles(roles);
        }

        log.info("Actualizando usuario con id: {}", idUsuario);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public void delete(Long idUsuario)
    {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con id: " + idUsuario));
        usuario.setEstado(0);
        usuarioRepository.save(usuario);
        log.info("Usuario con id {} eliminado lógicamente", idUsuario);
    }
}

