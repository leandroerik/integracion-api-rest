package com.example.consumir_api.service;

import com.example.consumir_api.client.JsonPlaceholderClient;
import com.example.consumir_api.dto.CommentDto;
import com.example.consumir_api.dto.PostDto;
import com.example.consumir_api.dto.PostResponseDto;
import com.example.consumir_api.dto.UserDto;
import com.example.consumir_api.exception.ApiException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final JsonPlaceholderClient client;

    public PostService(JsonPlaceholderClient client) {
        this.client = client;
    }

    @Cacheable("postsCache")
    public List<PostResponseDto> getMergedPosts() {

        List<PostDto> posts = client.getPosts();
        List<CommentDto> comments = client.getComments();
        List<UserDto> users = client.getUsers();


        Map<Long, List<CommentDto>> commentsByPost = comments.stream()
                .collect(Collectors.groupingBy(CommentDto::getPostId));


        Map<Long, UserDto> userById = users.stream()
                .collect(Collectors.toMap(UserDto::getId, u -> u));


        return posts.stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getBody(),
                        userById.get(post.getUserId()),
                        commentsByPost.getOrDefault(post.getId(), List.of())
                ))
                .collect(Collectors.toList());
    }

    public void deletePost(long id) {
        client.deletePost(id);
    }

}
