package com.example.app.model.dto;

import com.example.app.model.entity.Usuario;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * CAPA DE MODELO - DTOs (Data Transfer Objects)
 * Separan la capa de presentación de la entidad de dominio.
 */
public class UsuarioDTO {

    // ─── REQUEST (entrada) ────────────────────────────────────────────────────

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        private String nombre;

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        private String email;

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        private String password;

        private Usuario.Rol rol;
    }

    // ─── RESPONSE (salida) ────────────────────────────────────────────────────

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long id;
        private String nombre;
        private String email;
        private Usuario.Rol rol;
        private LocalDateTime fechaCreacion;
        private Boolean activo;
    }

    // ─── UPDATE REQUEST (actualización parcial) ───────────────────────────────

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {

        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        private String nombre;

        @Email(message = "El email no tiene un formato válido")
        private String email;

        private Usuario.Rol rol;
    }
}
