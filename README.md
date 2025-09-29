# Consumir API - Microservicio Spring Boot

Este proyecto consume APIs externas de posts, comentarios y usuarios, y expone endpoints REST para combinarlos.

## Tecnologías

- Java 17, Spring Boot 3
- RestTemplate para consumir APIs externas
- Spring Cache para mejorar performance
- Springdoc OpenAPI 2.7.0 para documentación
- Maven

## Endpoints

### Obtener posts mergeados
GET /posts

- Devuelve lista de posts con información de usuarios y comentarios.
- Ejemplo de respuesta:
```json
[
  {
    "id": 1,
    "title": "Título del post",
    "body": "Contenido del post",
    "user": {"id":1,"name":"Usuario","email":"u@ejemplo.com"},
    "comments":[{"id":1,"name":"Comentario","body":"Texto","email":"c@ejemplo.com"}]
  }
]
```
### Eliminar post por ID

DELETE /posts/{id}
- Respuesta exitosa: HTTP 204 No Content (sin body).
- Si no existe el post, devuelve HTTP 404 Not Found

## Compilación y ejecución
```
git clone https://github.com/leandroerik/integracion-api-rest.git
```
```
cd integracion-api-rest
```
```
mvn clean install
```
```
mvn spring-boot:run
```

Para consultar la docu en Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

## Decisiones técnicas

RestTemplate: Para llamar a APIs externas y traer posts, comentarios y usuarios
Patrón MVC:

- Modelo: DTOs (PostDto, UserDto, CommentDto) para manejar datos.

- Vista: No tenemos frontend, pero el “resultado” son los JSON dto que devuelve la API.

- Controlador: PostController maneja las rutas y decide qué enviar al cliente.

DTOs separados: Evitan acoplar datos externos con internos

Manejo de errores centralizado: para el catcheo de excepciones el @controllerAdvice

Cache: Reduce tiempos de respuesta en llamadas repetidas, uso el @Cacheable

Springdoc OpenAPI: Documentación automática de endpoints con swagger
