package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.PostCreateRequestDto;
import ru.shelter.dto.response.PostResponseDto;
import ru.shelter.model.Post;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    PostResponseDto toPostResponse (Post post);

    List<PostResponseDto> toPostResponseList (List<Post> posts);


//    @Mapping(target="id", ignore = true)
//    @Mapping(target="author", ignore = true)
//    @Mapping(target="creationTime", ignore = true)
//    @Mapping(target="imageAddress", ignore = true)
    Post fromDto (PostCreateRequestDto requestDto);
}