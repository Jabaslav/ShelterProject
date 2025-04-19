package ru.shelter.Interfaces;

import ru.shelter.dto.request.PostCreateRequestDto;
import ru.shelter.dto.response.PostResponseDto;

public interface PostService extends ServiceInterface<PostCreateRequestDto, PostResponseDto, Long>{
}
