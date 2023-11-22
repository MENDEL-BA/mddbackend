package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
@Log4j2
@Tag(name = "Post", description = "Post API.")
public class PostController {
    private final PostService service;
    private final ModelMapper mapper;

    public PostController(PostService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Reourne tous les posts
     *
     * @return une HTTP response avec List des Posts
     */
    @Operation(summary = "Retounre tous les posts Get all posts qur un sujet qui interrsse le user")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<?> findAllPostsByUser() {
        List<Post> posts = this.service.getAllPostByUserSubscription();
        if (!posts.isEmpty()) {
            return ResponseEntity.ok().body(this.mapper.map(posts, PostDto.class));
        }
        return ResponseEntity.noContent().build();

    }

    /**
     * Retourn un post par son Id
     *
     * @param id id du Post
     * @return le HTTP response avec le Post
     */
    @Operation(summary = "retourn un post par son Id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@Parameter(description = "id du post") @PathVariable("id") String id) {
        try {
            Post post = this.service.getById(Long.valueOf(id));
            if (post == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(this.mapper.map(post, Post.class));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Creation de posr
     *
     * @param postDto
     * @return le HTTP response avec le Post created
     */
    @Operation(summary = "Creation de post")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object de PostDto.class")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PostDto postDto) {
        Post postDto1 = this.service.create(this.mapper.map(postDto, Post.class));
        return ResponseEntity.ok().body(this.mapper.map(postDto1, Post.class));
    }
}
