package pe.edu.idat.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.idat.biblioteca.dto.request.PrestamoRequestDto;
import pe.edu.idat.biblioteca.dto.response.PrestamoResponseDto;
import pe.edu.idat.biblioteca.entity.Prestamo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrestamoMapper
{
    @Mapping(target = "idPrestamo", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "libro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaPrestamo", ignore = true)
    @Mapping(target = "fechaDevolucionReal", ignore = true)
    Prestamo toEntity(PrestamoRequestDto dto);

    @Mapping(target = "idUsuario", source = "usuario.idUsuario")
    @Mapping(target = "usernameUsuario", source = "usuario.username")
    @Mapping(target = "idLibro", source = "libro.idLibro")
    @Mapping(target = "tituloLibro", source = "libro.titulo")
    PrestamoResponseDto toResponse(Prestamo prestamo);

    List<PrestamoResponseDto> toResponseList(List<Prestamo> prestamos);
}

