package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PostCreateRequestDto;
import ru.shelter.dto.response.PostResponseDto;
import ru.shelter.interfaces.PostDao;
import ru.shelter.interfaces.UserDao;
import ru.shelter.mapper.PostMapper;
import ru.shelter.model.Post;
import ru.shelter.model.User;
import ru.shelter.serviceInterfaces.PostService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostImpl implements PostService {
    private final PostMapper postMapper;
    private final PostDao postDao;
    private final UserDao userDao;
    private final ImageStorageImpl imageStorage;

    @Override
    public PostResponseDto add(PostCreateRequestDto requestDto, MultipartFile image) {
        try {
            Post post = postMapper.fromDto(requestDto);
            User user = userDao.findById(requestDto.userId()).orElseThrow();
            if (imageStorage.validateImage(image)) // сохраняем изображение, если с ним все в порядке
                post.setImageAddress(imageStorage.saveImage(image));
            user.addPost(post);
            Post savedPost = postDao.save(post);
            log.info("Added post: ID {}", savedPost.getId());
            return postMapper.toPostResponse(savedPost);
        } catch (Exception e) {
            log.error("Error adding post: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<PostResponseDto> get(Long id) {
        try {
            Optional<Post> post = postDao.findById(id);
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
    public PostResponseDto update(PostCreateRequestDto requestDto, Long id, MultipartFile image) {
        return postDao.findById(id)
                .map(existingPost -> {
                    postMapper.updateFromDto(requestDto, existingPost);
                    postDao.save(existingPost);
                    log.info("Updated post: ID {}", id);
                    return postMapper.toPostResponse(existingPost);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public boolean remove(Long id) {
        try {
            postDao.deleteById(id);
            log.info("Deleted post: ID {}", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.warn("Post with ID {} not found", id);
            return false;
        }
    }

    @Override
    public List<PostResponseDto> getAll() {
        try {
            List<Post> posts = postDao.findAll();
            log.info("Retrieved {} posts", posts.size());
            return postMapper.toPostResponseList(posts);
        } catch (Exception e) {
            log.error("Error retrieving posts: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}