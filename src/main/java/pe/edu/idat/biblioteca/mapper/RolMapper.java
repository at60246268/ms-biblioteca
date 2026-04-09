package pe.edu.idat.biblioteca.mapper;

import org.mapstruct.*;
import pe.edu.idat.biblioteca.dto.request.RolRequestDto;
import pe.edu.idat.biblioteca.dto.response.RolResponseDto;
import pe.edu.idat.biblioteca.entity.Rol;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper
{
    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Rol toEntity(RolRequestDto dto);

    RolResponseDto toResponse(Rol rol);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateFromRequest(RolRequestDto dto, @MappingTarget Rol rol);

    List<RolResponseDto> toResponseList(List<Rol> roles);
}

