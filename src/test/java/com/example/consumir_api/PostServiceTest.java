package com.example.consumir_api;

import com.example.consumir_api.client.JsonPlaceholderClient;
import com.example.consumir_api.dto.CommentDto;
import com.example.consumir_api.dto.PostDto;
import com.example.consumir_api.dto.PostResponseDto;
import com.example.consumir_api.dto.UserDto;
import com.example.consumir_api.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PostServiceTest {

    @Mock
    private JsonPlaceholderClient client;

    @InjectMocks
    private PostService postService;

    public PostServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMergedPosts() {
        PostDto post = new PostDto(1, 1, "titulo", "body");
        CommentDto comment = new CommentDto(1, 1, "name", "email@test.com", "body comentario");
        UserDto user = new UserDto(1, "Leanne Graham", "Bret", "Sincere@april.biz");

        when(client.getPosts()).thenReturn(List.of(post));
        when(client.getComments()).thenReturn(List.of(comment));
        when(client.getUsers()).thenReturn(List.of(user));

        List<PostResponseDto> result = postService.getMergedPosts();
        assertEquals(1, result.size());
        assertEquals("titulo", result.get(0).getTitle());
        assertEquals("Leanne Graham", result.get(0).getUser().getName());
        assertEquals(1, result.get(0).getComments().size());
    }
}