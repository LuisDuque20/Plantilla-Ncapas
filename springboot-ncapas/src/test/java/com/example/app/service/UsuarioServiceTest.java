package com.example.app.service;

import com.example.app.exception.ConflictException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.dto.UsuarioDTO;
import com.example.app.model.entity.Usuario;
import com.example.app.model.mapper.UsuarioMapper;
import com.example.app.repository.UsuarioRepository;
import com.example.app.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * TEST UNITARIO - UsuarioService
 * Prueba la lógica de negocio de forma aislada usando mocks.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private UsuarioDTO.Request requestDTO;
    private Usuario usuarioEntidad;
    private UsuarioDTO.Response responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = UsuarioDTO.Request.builder()
                .nombre("Juan Pérez")
                .email("juan@example.com")
                .password("password123")
                .rol(Usuario.Rol.USER)
                .build();

        usuarioEntidad = Usuario.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .email("juan@example.com")
                .password("password123")
                .rol(Usuario.Rol.USER)
                .activo(true)
                .build();

        responseDTO = UsuarioDTO.Response.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .email("juan@example.com")
                .rol(Usuario.Rol.USER)
                .activo(true)
                .build();
    }

    @Test
    void crear_cuandoEmailNoExiste_deberiaCrearUsuario() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioMapper.toEntity(any())).thenReturn(usuarioEntidad);
        when(usuarioRepository.save(any())).thenReturn(usuarioEntidad);
        when(usuarioMapper.toResponse(any())).thenReturn(responseDTO);

        UsuarioDTO.Response resultado = usuarioService.crear(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getEmail()).isEqualTo("juan@example.com");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void crear_cuandoEmailYaExiste_deberiaLanzarConflictException() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.crear(requestDTO))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("juan@example.com");

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void obtenerPorId_cuandoExiste_deberiaRetornarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntidad));
        when(usuarioMapper.toResponse(any())).thenReturn(responseDTO);

        UsuarioDTO.Response resultado = usuarioService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void obtenerPorId_cuandoNoExiste_deberiaLanzarNotFoundException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.obtenerPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void eliminar_deberiaDesactivarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntidad));
        when(usuarioRepository.save(any())).thenReturn(usuarioEntidad);

        usuarioService.eliminar(1L);

        assertThat(usuarioEntidad.getActivo()).isFalse();
        verify(usuarioRepository, times(1)).save(usuarioEntidad);
    }
}
