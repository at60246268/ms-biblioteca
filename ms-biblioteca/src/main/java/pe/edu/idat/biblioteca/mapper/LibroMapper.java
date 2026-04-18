package pe.edu.idat.biblioteca.mapper;

import org.mapstruct.*;
import pe.edu.idat.biblioteca.dto.request.LibroRequestDto;
import pe.edu.idat.biblioteca.dto.response.LibroResponseDto;
import pe.edu.idat.biblioteca.entity.Libro;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibroMapper
{
    @Mapping(target = "idLibro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "cantidadDisponible", ignore = true)
    Libro toEntity(LibroRequestDto dto);

    LibroResponseDto toResponse(Libro libro);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idLibro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "cantidadDisponible", ignore = true)
    void updateFromRequest(LibroRequestDto dto, @MappingTarget Libro libro);

    List<LibroResponseDto> toResponseList(List<Libro> libros);
}

