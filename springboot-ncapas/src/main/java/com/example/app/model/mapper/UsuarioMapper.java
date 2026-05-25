package com.example.app.model.mapper;

import com.example.app.model.dto.UsuarioDTO;
import com.example.app.model.entity.Usuario;
import org.mapstruct.*;

/**
 * CAPA DE MODELO - Mapper
 * Convierte entre Entidad y DTO usando MapStruct (genera código en compilación).
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UsuarioMapper {

    /** Entidad → Response DTO */
    UsuarioDTO.Response toResponse(Usuario usuario);

    /** Request DTO → Entidad (ignora campos autogenerados) */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Usuario toEntity(UsuarioDTO.Request request);

    /** Actualiza una entidad existente con los datos del UpdateRequest (campos nulos ignorados) */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void updateEntityFromDto(UsuarioDTO.UpdateRequest dto, @MappingTarget Usuario usuario);
}
