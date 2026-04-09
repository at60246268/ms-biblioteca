package pe.edu.idat.biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.biblioteca.dto.request.LoginRequestDto;
import pe.edu.idat.biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.biblioteca.dto.response.ApiResponse;
import pe.edu.idat.biblioteca.dto.response.LoginResponseDto;
import pe.edu.idat.biblioteca.dto.response.UsuarioResponseDto;
import pe.edu.idat.biblioteca.security.JwtService;
import pe.edu.idat.biblioteca.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto dto)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String token = jwtService.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        log.info("Login exitoso para el usuario: {}", dto.getUsername());
        return ResponseEntity.ok(ApiResponse.ok(new LoginResponseDto(token, userDetails.getUsername(), roles)));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> register(@Valid @RequestBody UsuarioRequestDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Usuario registrado exitosamente", usuarioService.create(dto)));
    }
}

