package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PostRequest;
import ru.shelter.dto.response.PostResponse;
import ru.shelter.interfaces.PostRepo;
import ru.shelter.interfaces.UserRepo;
import ru.shelter.mapper.PostMapper;
import ru.shelter.model.Post;
import ru.shelter.model.User;
import ru.shelter.serviceInterfaces.PostInterface;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostService implements PostInterface {
    private final PostMapper postMapper;
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ImageStorageService imageStorage;

    @Override
    public PostResponse add(PostRequest requestDto, MultipartFile image) {
        try {
            Post post = postMapper.fromDto(requestDto);
            User user = userRepo.findById(requestDto.authorId()).orElseThrow();
            if (imageStorage.validateImage(image)) // сохраняем изображение, если с ним все в порядке
                post.setImageAddress(imageStorage.saveImage(image));
            Post savedPost = postRepo.save(post);
            log.info("Added post: ID {}", savedPost.getId());
            return postMapper.toPostResponse(savedPost);
        } catch (Exception e) {
            log.error("Error adding post: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<PostResponse> get(Long id) {
        try {
            Optional<Post> post = postRepo.findById(id);
            post.ifPresentOrElse(
                    p -> log.info("Retrieved post: ID {}", p.getId()),
                    () -> log.warn("Post with ID {} not found", id)
            );
            return post.map(postMapper::toPostResponse);
        } catch (Exception e) {
            log.error("Error retrieving post: ID {}", id);
            return Optional.empty();
        }
    }

    @Override
    public PostResponse update(PostRequest requestDto, Long id, MultipartFile image) {
        return postRepo.findById(id)
                .map(existingPost -> {
                    postMapper.updateFromDto(requestDto, existingPost);
                    postRepo.save(existingPost);
                    log.info("Updated post: ID {}", id);
                    return postMapper.toPostResponse(existingPost);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public boolean remove(Long id) {
        try {
            postRepo.deleteById(id);
            log.info("Deleted post: ID {}", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.warn("Post with ID {} not found", id);
            return false;
        }
    }

    @Override
    public List<PostResponse> getAll() {
        try {
            List<Post> posts = postRepo.findAll();
            log.info("Retrieved {} posts", posts.size());
            return postMapper.toPostResponseList(posts);
        } catch (Exception e) {
            log.error("Error retrieving posts: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}