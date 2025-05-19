package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.PostRequest;
import ru.shelter.dto.request.PostUpdateRequest;
import ru.shelter.dto.response.PostResponse;
import ru.shelter.model.Post;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    //@Mapping(source = "author.id", target = "authorId")
    PostResponse toPostResponse(Post post);

//    @Mapping(target = "author", expression = "java(mapUser(requestDto.authorId()))")
//    @Mapping(target = "creationTime", ignore = true)
    Post fromDto(PostRequest requestDto);

//    default User mapUser(Long authorId) {
//        User user = new User();
//        user.setId(authorId);
//        return user;
//    }
// Что это?

    List<PostResponse> toPostResponseList(List<Post> posts);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "creationTime", ignore = true)
    void updateFromDto(PostUpdateRequest dto, @MappingTarget Post post);
}