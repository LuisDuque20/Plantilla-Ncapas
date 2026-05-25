package com.example.app.repository;

import com.example.app.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CAPA DE REPOSITORIO (Acceso a datos)
 * Extiende JpaRepository para obtener CRUD completo de forma automática.
 * Añade consultas personalizadas cuando se necesitan.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /** Busca un usuario activo por email */
    Optional<Usuario> findByEmailAndActivoTrue(String email);

    /** Verifica si ya existe un email registrado */
    boolean existsByEmail(String email);

    /** Lista usuarios activos con paginación */
    Page<Usuario> findAllByActivoTrue(Pageable pageable);

    /** Búsqueda por nombre o email (case-insensitive) */
    @Query("""
        SELECT u FROM Usuario u
        WHERE u.activo = true
          AND (LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))
            OR LOWER(u.email)  LIKE LOWER(CONCAT('%', :termino, '%')))
        """)
    Page<Usuario> buscarPorTermino(@Param("termino") String termino, Pageable pageable);
}
