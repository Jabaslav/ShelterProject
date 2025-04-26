package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.PostCreateRequestDto;
import ru.shelter.dto.response.PostResponseDto;
import ru.shelter.model.Post;
import ru.shelter.model.User;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    @Mapping(source = "author.id", target = "authorId")
    PostResponseDto toPostResponse(Post post);

    @Mapping(target = "author", expression = "java(mapUser(requestDto.authorId()))")
    @Mapping(target = "creationTime", ignore = true)
    Post fromDto(PostCreateRequestDto requestDto);

    default User mapUser(Long authorId) {
        User user = new User();
        user.setId(authorId);
        return user;
    }

    List<PostResponseDto> toPostResponseList(List<Post> posts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    void updateFromDto(PostCreateRequestDto dto, @MappingTarget Post post);
}