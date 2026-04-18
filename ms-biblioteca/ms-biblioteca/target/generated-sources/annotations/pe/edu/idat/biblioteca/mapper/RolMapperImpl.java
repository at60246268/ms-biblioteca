package pe.edu.idat.biblioteca.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.idat.biblioteca.dto.request.RolRequestDto;
import pe.edu.idat.biblioteca.dto.response.RolResponseDto;
import pe.edu.idat.biblioteca.entity.Rol;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T17:59:21-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class RolMapperImpl implements RolMapper {

    @Override
    public Rol toEntity(RolRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Rol.RolBuilder rol = Rol.builder();

        rol.nombre( dto.getNombre() );
        rol.descripcion( dto.getDescripcion() );

        return rol.build();
    }

    @Override
    public RolResponseDto toResponse(Rol rol) {
        if ( rol == null ) {
            return null;
        }

        Long idRol = null;
        String nombre = null;
        String descripcion = null;
        Integer estado = null;

        idRol = rol.getIdRol();
        nombre = rol.getNombre();
        descripcion = rol.getDescripcion();
        estado = rol.getEstado();

        RolResponseDto rolResponseDto = new RolResponseDto( idRol, nombre, descripcion, estado );

        return rolResponseDto;
    }

    @Override
    public void updateFromRequest(RolRequestDto dto, Rol rol) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getNombre() != null ) {
            rol.setNombre( dto.getNombre() );
        }
        if ( dto.getDescripcion() != null ) {
            rol.setDescripcion( dto.getDescripcion() );
        }
    }

    @Override
    public List<RolResponseDto> toResponseList(List<Rol> roles) {
        if ( roles == null ) {
            return null;
        }

        List<RolResponseDto> list = new ArrayList<RolResponseDto>( roles.size() );
        for ( Rol rol : roles ) {
            list.add( toResponse( rol ) );
        }

        return list;
    }
}
