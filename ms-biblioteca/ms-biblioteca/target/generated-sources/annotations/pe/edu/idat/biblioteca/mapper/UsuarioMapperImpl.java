package pe.edu.idat.biblioteca.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.idat.biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.biblioteca.dto.response.UsuarioResponseDto;
import pe.edu.idat.biblioteca.entity.Usuario;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T17:59:21-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.username( dto.getUsername() );
        usuario.nombres( dto.getNombres() );
        usuario.apellidos( dto.getApellidos() );
        usuario.email( dto.getEmail() );

        return usuario.build();
    }

    @Override
    public UsuarioResponseDto toResponse(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        List<String> roles = null;
        Long idUsuario = null;
        String username = null;
        String nombres = null;
        String apellidos = null;
        String email = null;
        Integer estado = null;

        roles = rolesToNombres( usuario.getRoles() );
        idUsuario = usuario.getIdUsuario();
        username = usuario.getUsername();
        nombres = usuario.getNombres();
        apellidos = usuario.getApellidos();
        email = usuario.getEmail();
        estado = usuario.getEstado();

        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto( idUsuario, username, nombres, apellidos, email, estado, roles );

        return usuarioResponseDto;
    }

    @Override
    public void updateFromRequest(UsuarioRequestDto dto, Usuario usuario) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getUsername() != null ) {
            usuario.setUsername( dto.getUsername() );
        }
        if ( dto.getNombres() != null ) {
            usuario.setNombres( dto.getNombres() );
        }
        if ( dto.getApellidos() != null ) {
            usuario.setApellidos( dto.getApellidos() );
        }
        if ( dto.getEmail() != null ) {
            usuario.setEmail( dto.getEmail() );
        }
    }

    @Override
    public List<UsuarioResponseDto> toResponseList(List<Usuario> usuarios) {
        if ( usuarios == null ) {
            return null;
        }

        List<UsuarioResponseDto> list = new ArrayList<UsuarioResponseDto>( usuarios.size() );
        for ( Usuario usuario : usuarios ) {
            list.add( toResponse( usuario ) );
        }

        return list;
    }
}
