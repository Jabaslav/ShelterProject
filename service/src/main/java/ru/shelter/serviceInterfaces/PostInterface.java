package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PostRequest;
import ru.shelter.dto.response.PostResponse;

public interface PostInterface extends ServiceInterface<PostRequest, PostResponse, Long>{
    PostResponse add(PostRequest requestDto, MultipartFile image);

    PostResponse update(PostRequest requestDto, Long id, MultipartFile image);
}
