# 🏗️ Plantilla Spring Boot — Arquitectura N-Capas

## Estructura del Proyecto

```
src/main/java/com/example/app/
│
├── 📦 controller/          ← CAPA DE PRESENTACIÓN (REST API)
│   └── UsuarioController
│
├── 📦 service/             ← CAPA DE NEGOCIO
│   ├── UsuarioService          (interfaz)
│   └── impl/
│       └── UsuarioServiceImpl  (implementación)
│
├── 📦 repository/          ← CAPA DE ACCESO A DATOS
│   └── UsuarioRepository       (extiende JpaRepository)
│
├── 📦 model/               ← MODELOS
│   ├── entity/             ← Entidades JPA (mapeo a tablas)
│   ├── dto/                ← Objetos de transferencia (request/response)
│   └── mapper/             ← Conversores Entidad ↔ DTO (MapStruct)
│
├── 📦 exception/           ← MANEJO DE EXCEPCIONES
│   ├── ResourceNotFoundException
│   ├── ConflictException
│   └── GlobalExceptionHandler  (@RestControllerAdvice)
│
├── 📦 config/              ← CONFIGURACIÓN
│   └── WebConfig               (CORS, etc.)
│
└── 📦 util/                ← UTILIDADES
    └── ApiResponse             (wrapper estándar de respuestas)
```

## Flujo de una Petición

```
HTTP Request
     │
     ▼
[Controller]  → valida entrada (@Valid), llama al servicio
     │
     ▼
[Service]     → lógica de negocio, usa mapper y repositorio
     │
     ▼
[Repository]  → consulta/actualiza la base de datos
     │
     ▼
[Mapper]      → convierte Entidad → DTO
     │
     ▼
HTTP Response (ApiResponse<T>)
```

## Endpoints Disponibles

| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/v1/usuarios` | Crear usuario |
| GET | `/api/v1/usuarios` | Listar usuarios (paginado) |
| GET | `/api/v1/usuarios/{id}` | Obtener por ID |
| GET | `/api/v1/usuarios/buscar?termino=x` | Buscar por nombre/email |
| PUT | `/api/v1/usuarios/{id}` | Actualizar usuario |
| DELETE | `/api/v1/usuarios/{id}` | Eliminar (soft-delete) |

## Cómo Agregar un Nuevo Recurso

Sigue estos pasos para cada nueva entidad (ej: `Producto`):

1. **Entity** → `model/entity/Producto.java`
2. **DTO** → `model/dto/ProductoDTO.java` (inner classes: Request, Response, UpdateRequest)
3. **Mapper** → `model/mapper/ProductoMapper.java`
4. **Repository** → `repository/ProductoRepository.java`
5. **Service (interfaz)** → `service/ProductoService.java`
6. **Service (impl)** → `service/impl/ProductoServiceImpl.java`
7. **Controller** → `controller/ProductoController.java`
8. **Test** → `test/.../ProductoServiceTest.java`

## Cómo Ejecutar

```bash
# Con Maven
./mvnw spring-boot:run

# La API estará en: http://localhost:8080
# Consola H2 en:    http://localhost:8080/h2-console
# Actuator en:      http://localhost:8080/actuator/health
```

## Tecnologías

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| Spring Boot | 3.2.5 | Framework principal |
| Spring Data JPA | - | ORM / Repositorios |
| Hibernate | - | Implementación JPA |
| MapStruct | 1.5.5 | Mapeo Entidad ↔ DTO |
| Lombok | 1.18.32 | Reducción de boilerplate |
| H2 | - | BD en memoria (dev) |
| JUnit 5 + Mockito | - | Tests unitarios |

## Próximos Pasos Sugeridos

- [ ] Cambiar H2 por PostgreSQL/MySQL en `application.properties`
- [ ] Agregar Spring Security + JWT
- [ ] Configurar Flyway/Liquibase para migraciones
- [ ] Agregar documentación con SpringDoc OpenAPI (Swagger)
- [ ] Configurar perfiles (`dev`, `prod`) con `application-{profile}.properties`
