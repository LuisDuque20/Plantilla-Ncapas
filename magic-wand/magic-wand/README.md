# The Magic Wand - API REST

Sistema de manejo de artículos mágicos para The Magic Wand.

## Tecnologías
- **Java 17**
- **Spring Boot 3.2.5**
- **Maven**
- **PostgreSQL**
- **Spring Data JPA / Hibernate**
- **MapStruct** (mapeo de entidades)
- **Lombok**
- **Bean Validation**

## Arquitectura N-Capas

```
src/main/java/com/magicwand/
├── controller/          → Capa de presentación (REST)
│   ├── ProviderController.java
│   └── ArticleController.java
├── service/             → Capa de lógica de negocio (interfaces)
│   ├── MagicProviderService.java
│   └── MagicArticleService.java
│   └── impl/            → Implementaciones
│       ├── MagicProviderServiceImpl.java
│       └── MagicArticleServiceImpl.java
├── repository/          → Capa de acceso a datos
│   ├── MagicProviderRepository.java
│   └── MagicArticleRepository.java
├── entity/              → Entidades JPA
│   ├── MagicProvider.java
│   └── MagicArticle.java
├── dto/
│   ├── request/         → DTOs de entrada
│   └── response/        → DTOs de salida
├── mapper/              → MapStruct mappers
├── enums/               → Enums compartidos
└── exception/           → Manejo centralizado de errores
```

## Configuración de Base de Datos

1. Crear la base de datos en PostgreSQL:
```sql
CREATE DATABASE magic_wand_db;
```

2. Configurar credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/magic_wand_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Ejecutar el proyecto

```bash
mvn spring-boot:run
```

## Endpoints

### Proveedores `/providers`

| Método | Path | Descripción |
|--------|------|-------------|
| POST | `/providers` | Crear proveedor |
| GET | `/providers/{id}` | Obtener proveedor por ID |
| PUT | `/providers/{id}` | Actualizar proveedor |
| DELETE | `/providers/{id}` | Eliminar proveedor (sin artículos) |

### Artículos `/artefacts`

| Método | Path | Descripción |
|--------|------|-------------|
| POST | `/artefacts` | Crear artículo |
| GET | `/artefacts?category=X&maxPrice=X&provider=X` | Listar con filtros |
| GET | `/artefacts/{id}` | Obtener artículo por ID |
| PUT | `/artefacts/{id}` | Actualizar artículo |
| DELETE | `/artefacts/{id}` | Eliminar artículo |

## Ejemplos de peticiones

### Crear proveedor
```json
POST /providers
{
  "name": "Gremio de los Alquimistas",
  "type": "POCION"
}
```

### Crear artículo
```json
POST /artefacts
{
  "name": "Poción de Vida Mayor",
  "type": "POCION",
  "price": 150.00,
  "providerId": 1
}
```

### Listar artículos con filtros
```
GET /artefacts?category=POCION&maxPrice=200&provider=1
```

## Códigos HTTP

| Código | Significado |
|--------|-------------|
| 200 | Operación exitosa |
| 201 | Recurso creado |
| 204 | Eliminado correctamente |
| 400 | Error de validación de campos |
| 404 | Entidad no encontrada |
| 409 | Conflicto (nombre duplicado, proveedor con artículos) |
| 422 | Regla de negocio no cumplida (tipo no coincide) |

## Reglas de negocio

- Los nombres de artículos y proveedores son **únicos sin distinción de mayúsculas**.
- El precio de un artículo debe ser **mayor a 0**.
- El tipo del artículo debe **coincidir con el tipo del proveedor** asignado.
- No se puede eliminar un proveedor que tenga artículos asociados.
