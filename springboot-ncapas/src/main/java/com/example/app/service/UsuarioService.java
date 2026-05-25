package com.example.app.service;

import com.example.app.model.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CAPA DE SERVICIO - Interfaz (contrato)
 * Define las operaciones de negocio disponibles.
 * La implementación está en service/impl/UsuarioServiceImpl.java
 */
public interface UsuarioService {

    UsuarioDTO.Response crear(UsuarioDTO.Request request);

    UsuarioDTO.Response obtenerPorId(Long id);

    Page<UsuarioDTO.Response> listarTodos(Pageable pageable);

    Page<UsuarioDTO.Response> buscar(String termino, Pageable pageable);

    UsuarioDTO.Response actualizar(Long id, UsuarioDTO.UpdateRequest request);

    void eliminar(Long id);  // soft-delete (activo = false)
}
