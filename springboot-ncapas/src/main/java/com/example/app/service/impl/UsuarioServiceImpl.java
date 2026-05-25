package com.example.app.service.impl;

import com.example.app.exception.ConflictException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.dto.UsuarioDTO;
import com.example.app.model.entity.Usuario;
import com.example.app.model.mapper.UsuarioMapper;
import com.example.app.repository.UsuarioRepository;
import com.example.app.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CAPA DE SERVICIO - Implementación
 * Contiene la lógica de negocio. Orquesta repositorios y mappers.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public UsuarioDTO.Response crear(UsuarioDTO.Request request) {
        log.debug("Creando usuario con email: {}", request.getEmail());

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Ya existe un usuario con el email: " + request.getEmail());
        }

        Usuario usuario = usuarioMapper.toEntity(request);

        // TODO: encriptar contraseña antes de guardar
        // usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        if (usuario.getRol() == null) {
            usuario.setRol(Usuario.Rol.USER);
        }

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario creado con ID: {}", guardado.getId());

        return usuarioMapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO.Response obtenerPorId(Long id) {
        log.debug("Buscando usuario con ID: {}", id);
        Usuario usuario = findUsuarioById(id);
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO.Response> listarTodos(Pageable pageable) {
        log.debug("Listando todos los usuarios activos");
        return usuarioRepository.findAllByActivoTrue(pageable)
                .map(usuarioMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO.Response> buscar(String termino, Pageable pageable) {
        log.debug("Buscando usuarios con término: {}", termino);
        return usuarioRepository.buscarPorTermino(termino, pageable)
                .map(usuarioMapper::toResponse);
    }

    @Override
    @Transactional
    public UsuarioDTO.Response actualizar(Long id, UsuarioDTO.UpdateRequest request) {
        log.debug("Actualizando usuario con ID: {}", id);

        Usuario usuario = findUsuarioById(id);

        if (request.getEmail() != null && !request.getEmail().equals(usuario.getEmail())
                && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Ya existe un usuario con el email: " + request.getEmail());
        }

        usuarioMapper.updateEntityFromDto(request, usuario);
        Usuario actualizado = usuarioRepository.save(usuario);

        log.info("Usuario actualizado con ID: {}", actualizado.getId());
        return usuarioMapper.toResponse(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.debug("Eliminando (soft-delete) usuario con ID: {}", id);
        Usuario usuario = findUsuarioById(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        log.info("Usuario desactivado con ID: {}", id);
    }

    // ─── Métodos privados de apoyo ────────────────────────────────────────────

    private Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .filter(Usuario::getActivo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }
}
