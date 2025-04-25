package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.Interfaces.PostService;
import ru.shelter.dto.request.PostCreateRequestDto;
import ru.shelter.dto.response.PostResponseDto;
import ru.shelter.interfaces.PostDao;
import ru.shelter.mapper.PostMapper;
import ru.shelter.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostImpl implements PostService {
    private final PostMapper postMapper;
    private final PostDao postDao;
    private final ImageStorageImpl imageStorage;

    @Override
    public PostResponseDto add(PostCreateRequestDto requestDto) {
        try {
            Post post = postMapper.fromDto(requestDto); // создание entity через маппер
            Post savedPost = postDao.save(post); // сохраняем поста в бд
            PostResponseDto response = postMapper.toPostResponse(savedPost); // готовим ответ на запрос
            log.info("Adding post: {}", response);
            return response;
        }
        catch (Exception e)
        {
            log.error("Add post error:", e);
        };
        return null;
    }

    @Override
    public PostResponseDto addWithImage(PostCreateRequestDto requestDto, MultipartFile image) {
        try {
            Post post = postMapper.fromDto(requestDto); // создание entity через маппер

            if (image!=null && !image.isEmpty())
            {
                post.setImageAddress(imageStorage.saveImage(image));
            }
            Post savedPost = postDao.save(post); // сохраняем питомца в бд
            PostResponseDto response = postMapper.toPostResponse(savedPost); // готовим ответ на запрос
            log.info("Adding post: {}", response);
            return response;
        }
        catch (Exception e)
        {
            log.error("Add Post error:", e);
        };
        return null;
    }

    @Override
    public Optional<PostResponseDto> get(Long id) {
        try {
            Optional<PostResponseDto> response = postDao.findById(id).map(postMapper::toPostResponse);
            if (response.isPresent())
                log.info("Get post:", response);
            else
                log.info("Post with id", id, "not found");
            return response;
        }
        catch (Exception e)
        {
            log.error ("Get post error:", e);
            return Optional.empty();
        }
    }

    @Override
    public PostResponseDto update(PostCreateRequestDto requestDto, Long id) {
        try {
            Optional<Post> Post = postDao.findById(id);
            if (Post.isPresent())
            {
                Post post = postMapper.fromDto(requestDto);
                post.setId(id);
                postDao.save(post);
                log.info("Updating post: {}", requestDto);
                return (postMapper.toPostResponse(post));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update post error:", e);
            return null;
        }
    }

    @Override
    public PostResponseDto updateWithImage(PostCreateRequestDto requestDto, Long id, MultipartFile image) {
        try {
            Optional<Post> Post = postDao.findById(id);
            if (Post.isPresent())
            {
                Post post = postMapper.fromDto(requestDto);
                post.setId(id);
                if (image!=null && !image.isEmpty())
                {
                    post.setImageAddress(imageStorage.saveImage(image));
                }
                postDao.save(post);
                log.info("Updating post: {}", requestDto);
                return (postMapper.toPostResponse(post));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update post error:", e);
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            postDao.deleteById(id);
            log.info("Deleted post: {}", id);
            return true;
        }
        catch (Exception e)
        {
            log.error("Deleting post error:", e);
            return false;
        }
    }

    @Override
    public List<PostResponseDto> getAll() {
        try
        {
            List<Post> posts = postDao.findAll();
            log.info("GetAll posts successfully:{}", posts);
            return (postMapper.toPostResponseList(posts));
        }
        catch (Exception e)
        {
            log.error("GetAll post error:", e);
            return Collections.emptyList();
        }
    }
}
