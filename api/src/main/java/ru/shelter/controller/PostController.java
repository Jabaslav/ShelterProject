package ru.shelter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PostRequest;
import ru.shelter.dto.response.PostResponse;
import ru.shelter.impl.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestPart(value="postDto") PostRequest requestDto,
            @RequestPart(value="image", required = false) MultipartFile image) {
        return new ResponseEntity<>(postService.add(requestDto, image), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        Optional<PostResponse> response = postService.get(id);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @RequestPart(value="postDto") PostRequest requestDto,
            @RequestPart(value="image", required = false) MultipartFile image)
    {
            return new ResponseEntity<>(postService.update(requestDto, id, image), HttpStatus.OK); // Http 200
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.remove(id), HttpStatus.NO_CONTENT); // Http 204
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAll();
        return ResponseEntity.ok(posts);
    }
}