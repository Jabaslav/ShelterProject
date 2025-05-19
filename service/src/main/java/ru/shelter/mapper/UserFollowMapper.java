package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.UserFollowRequest;
import ru.shelter.dto.request.UserRequest;
import ru.shelter.dto.response.UserFollowResponse;
import ru.shelter.dto.response.UserResponse;
import ru.shelter.model.User;
import ru.shelter.model.UserFollow;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserFollowMapper {

    UserFollowResponse toUserFollowResponse (UserFollow userFollow);

    List<UserFollowResponse> toUserFollowResponseList (List<UserFollow> userFollows);

    UserFollow fromDto(UserFollowRequest requestDto);
}