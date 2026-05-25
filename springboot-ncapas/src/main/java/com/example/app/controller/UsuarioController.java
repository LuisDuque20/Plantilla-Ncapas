package com.example.app.controller;

import com.example.app.model.dto.UsuarioDTO;
import com.example.app.service.UsuarioService;
import com.example.app.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CAPA DE PRESENTACIÓN - Controlador REST
 * Expone los endpoints HTTP. Solo delega al servicio, sin lógica de negocio.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /** POST /api/v1/usuarios → Crear usuario */
    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDTO.Response>> crear(
            @Valid @RequestBody UsuarioDTO.Request request) {

        UsuarioDTO.Response response = usuarioService.crear(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario creado exitosamente", response));
    }

    /** GET /api/v1/usuarios/{id} → Obtener por ID */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO.Response>> obtenerPorId(@PathVariable Long id) {
        UsuarioDTO.Response response = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /** GET /api/v1/usuarios → Listar todos (paginado) */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UsuarioDTO.Response>>> listarTodos(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        Page<UsuarioDTO.Response> page = usuarioService.listarTodos(pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }

    /** GET /api/v1/usuarios/buscar?termino=xxx → Buscar */
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<Page<UsuarioDTO.Response>>> buscar(
            @RequestParam String termino,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<UsuarioDTO.Response> page = usuarioService.buscar(termino, pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }

    /** PUT /api/v1/usuarios/{id} → Actualizar */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO.Response>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO.UpdateRequest request) {

        UsuarioDTO.Response response = usuarioService.actualizar(id, request);
        return ResponseEntity.ok(ApiResponse.success("Usuario actualizado exitosamente", response));
    }

    /** DELETE /api/v1/usuarios/{id} → Eliminar (soft-delete) */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.success("Usuario eliminado exitosamente", null));
    }
}
