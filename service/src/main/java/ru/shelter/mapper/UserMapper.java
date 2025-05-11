package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.UserRequest;
import ru.shelter.dto.response.UserResponse;
import ru.shelter.model.User;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toUserResponse (User user);

    List<UserResponse> toUserResponseList (List<User> users);

//    @Mapping(target="id", ignore = true)
//    @Mapping(target="password", ignore = true)
//    @Mapping(target="registerTime", ignore=true)
//    @Mapping(target="profilePicAddress", ignore=true)
    User fromDto(UserRequest requestDto);
}
