package pe.edu.idat.biblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.idat.biblioteca.dto.request.PrestamoRequestDto;
import pe.edu.idat.biblioteca.dto.response.PrestamoResponseDto;
import pe.edu.idat.biblioteca.entity.Libro;
import pe.edu.idat.biblioteca.entity.Prestamo;
import pe.edu.idat.biblioteca.entity.Usuario;
import pe.edu.idat.biblioteca.mapper.PrestamoMapper;
import pe.edu.idat.biblioteca.repository.LibroRepository;
import pe.edu.idat.biblioteca.repository.PrestamoRepository;
import pe.edu.idat.biblioteca.repository.UsuarioRepository;
import pe.edu.idat.biblioteca.service.PrestamoService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService
{
    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final PrestamoMapper prestamoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoResponseDto> findAll()
    {
        return prestamoMapper.toResponseList(prestamoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PrestamoResponseDto findById(Long idPrestamo)
    {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new RuntimeException("No existe el préstamo con id: " + idPrestamo));
        return prestamoMapper.toResponse(prestamo);
    }

    @Override
    @Transactional
    public PrestamoResponseDto create(PrestamoRequestDto dto)
    {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("No existe el usuario con id: " + dto.getIdUsuario()));

        Libro libro = libroRepository.findById(dto.getIdLibro())
                .orElseThrow(() -> new RuntimeException("No existe el libro con id: " + dto.getIdLibro()));

        if (libro.getCantidadDisponible() <= 0)
        {
            throw new RuntimeException("No hay ejemplares disponibles del libro: " + libro.getTitulo());
        }

        Prestamo prestamo = prestamoMapper.toEntity(dto);
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());

        libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
        libroRepository.save(libro);

        log.info("Registrando préstamo: usuario={}, libro={}", usuario.getUsername(), libro.getTitulo());
        return prestamoMapper.toResponse(prestamoRepository.save(prestamo));
    }

    @Override
    @Transactional
    public PrestamoResponseDto registrarDevolucion(Long idPrestamo)
    {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new RuntimeException("No existe el préstamo con id: " + idPrestamo));

        if (prestamo.getEstado() == 0)
        {
            throw new RuntimeException("El préstamo con id " + idPrestamo + " ya fue devuelto");
        }

        prestamo.setFechaDevolucionReal(LocalDate.now());
        prestamo.setEstado(0);

        Libro libro = prestamo.getLibro();
        libro.setCantidadDisponible(libro.getCantidadDisponible() + 1);
        libroRepository.save(libro);

        log.info("Registrando devolución del préstamo id: {}", idPrestamo);
        return prestamoMapper.toResponse(prestamoRepository.save(prestamo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoResponseDto> findByUsuario(Long idUsuario)
    {
        return prestamoMapper.toResponseList(prestamoRepository.findByUsuarioIdUsuario(idUsuario));
    }
}

