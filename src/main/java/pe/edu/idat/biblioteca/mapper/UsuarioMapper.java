package pe.edu.idat.biblioteca.mapper;

import org.mapstruct.*;
import pe.edu.idat.biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.biblioteca.dto.response.UsuarioResponseDto;
import pe.edu.idat.biblioteca.entity.Rol;
import pe.edu.idat.biblioteca.entity.Usuario;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper
{
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Usuario toEntity(UsuarioRequestDto dto);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToNombres")
    UsuarioResponseDto toResponse(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateFromRequest(UsuarioRequestDto dto, @MappingTarget Usuario usuario);

    List<UsuarioResponseDto> toResponseList(List<Usuario> usuarios);

    @Named("rolesToNombres")
    default List<String> rolesToNombres(Set<Rol> roles)
    {
        if (roles == null) return List.of();
        return roles.stream().map(Rol::getNombre).collect(Collectors.toList());
    }
}

