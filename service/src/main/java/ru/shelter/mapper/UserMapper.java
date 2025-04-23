package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.User;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponseDto toUserResponse (User user);

    List<UserResponseDto> toUserResponseList (List<User> users);

//    @Mapping(target="id", ignore = true)
//    @Mapping(target="password", ignore = true)
//    @Mapping(target="registerTime", ignore=true)
//    @Mapping(target="profilePicAddress", ignore=true)
    User fromDto(UserCreateRequestDto requestDto);
}
