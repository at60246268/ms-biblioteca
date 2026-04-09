package pe.edu.idat.biblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.idat.biblioteca.dto.request.LibroRequestDto;
import pe.edu.idat.biblioteca.dto.response.LibroResponseDto;
import pe.edu.idat.biblioteca.entity.Libro;
import pe.edu.idat.biblioteca.mapper.LibroMapper;
import pe.edu.idat.biblioteca.repository.LibroRepository;
import pe.edu.idat.biblioteca.service.LibroService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService
{
    private final LibroRepository libroRepository;
    private final LibroMapper libroMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LibroResponseDto> findAll()
    {
        return libroMapper.toResponseList(libroRepository.findByEstado(1));
    }

    @Override
    @Transactional(readOnly = true)
    public LibroResponseDto findById(Long idLibro)
    {
        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new RuntimeException("No existe el libro con id: " + idLibro));
        return libroMapper.toResponse(libro);
    }

    @Override
    @Transactional
    public LibroResponseDto create(LibroRequestDto dto)
    {
        log.info("Registrando libro: {}", dto.getTitulo());
        return libroMapper.toResponse(libroRepository.save(libroMapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public LibroResponseDto update(Long idLibro, LibroRequestDto dto)
    {
        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new RuntimeException("No existe el libro con id: " + idLibro));
        libroMapper.updateFromRequest(dto, libro);
        log.info("Actualizando libro con id: {}", idLibro);
        return libroMapper.toResponse(libroRepository.save(libro));
    }

    @Override
    @Transactional
    public void delete(Long idLibro)
    {
        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new RuntimeException("No existe el libro con id: " + idLibro));
        libro.setEstado(0);
        libroRepository.save(libro);
        log.info("Libro con id {} eliminado lógicamente", idLibro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibroResponseDto> findByTitulo(String titulo)
    {
        return libroMapper.toResponseList(libroRepository.findByTituloContainingIgnoreCaseAndEstado(titulo, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibroResponseDto> findByAutor(String autor)
    {
        return libroMapper.toResponseList(libroRepository.findByAutorContainingIgnoreCaseAndEstado(autor, 1));
    }
}

