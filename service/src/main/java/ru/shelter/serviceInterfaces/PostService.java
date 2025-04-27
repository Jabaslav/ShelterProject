package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PostCreateRequestDto;
import ru.shelter.dto.response.PostResponseDto;

public interface PostService extends ServiceInterface<PostCreateRequestDto, PostResponseDto, Long>{
    PostResponseDto addWithImage(PostCreateRequestDto requestDto, MultipartFile image);

    PostResponseDto updateWithImage(PostCreateRequestDto requestDto, Long id, MultipartFile image);
}
