package com.example.consumir_api.controller;

import com.example.consumir_api.dto.PostResponseDto;
import com.example.consumir_api.service.PostService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Tag(name = "Posts", description = "Endpoints para gestionar posts, usuarios y comentarios")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(
            summary = "Obtener posts con comentarios y usuarios",
            description = "Devuelve la lista completa de posts, incluyendo información de usuarios y comentarios relacionados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de posts obtenida correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostResponseDto.class),
                            examples = @ExampleObject(value = "[{\"id\":1,\"title\":\"Post 1\",\"user\":\"Juan\",\"comments\":[{\"id\":10,\"text\":\"Comentario ejemplo\"}]}]")
                    )
            )
    })
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        long start = System.currentTimeMillis();
        List<PostResponseDto> response = postService.getMergedPosts();
        System.out.println("Tiempo de ejecución: " + (System.currentTimeMillis() - start) + "ms");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un post por ID", description = "Permite eliminar un post según su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Post no encontrado")
    })
    public ResponseEntity<Void> deletePost(@PathVariable("id") long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}