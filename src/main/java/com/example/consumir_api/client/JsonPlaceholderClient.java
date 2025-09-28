package com.example.consumir_api.client;

import com.example.consumir_api.config.ExternalApiProperties;
import com.example.consumir_api.dto.CommentDto;
import com.example.consumir_api.dto.PostDto;

import com.example.consumir_api.dto.UserDto;
import com.example.consumir_api.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class JsonPlaceholderClient {

    private static final Logger log = LoggerFactory.getLogger(JsonPlaceholderClient.class);

    private final RestTemplate restTemplate;
    private final ExternalApiProperties properties;

    public JsonPlaceholderClient(RestTemplate restTemplate, ExternalApiProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public List<PostDto> getPosts() {
        return fetchData("/posts", PostDto[].class);
    }

    public List<CommentDto> getComments() {
        return fetchData("/comments", CommentDto[].class);
    }

    public List<UserDto> getUsers() {
        return fetchData("/users", UserDto[].class);
    }

    private <T> List<T> fetchData(String path, Class<T[]> clazz) {
        try {
            String url = properties.getBaseUrl() + path;
            log.info("Llamando API externa: {}", url);
            ResponseEntity<T[]> resp = restTemplate.getForEntity(url, clazz);
            return resp.getBody() == null ? List.of() : Arrays.asList(resp.getBody());
        } catch (RestClientException ex) {
            log.error("Error llamando API externa {}: {}", path, ex.getMessage());
            throw new ApiException("Fallo al llamar API externa: " + path, ex);
        }
    }
    public void deletePost(long id) {
        try {

            String url = properties.getBaseUrl() + "/posts/" + id;
            log.info("Llamando DELETE a: {}", url);
            restTemplate.delete(url);

        } catch (RestClientException ex) {

            log.error("Error borrando post {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error al eliminar post " + id, ex);

        }
    }

}