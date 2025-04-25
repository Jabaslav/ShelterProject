package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.FriendListRequestDto;
import ru.shelter.dto.response.FriendListResponseDto;
import ru.shelter.model.FriendList;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendListMapper {

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "friendId", target = "friendId")
    @Mapping(source = "status", target = "status")
    FriendList fromDto(FriendListRequestDto requestDto);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "friendId", target = "friendId")
    @Mapping(source = "status", target = "status")
    FriendListResponseDto toDto(FriendList friendList);

    void updateFromDto(FriendListRequestDto requestDto, @MappingTarget FriendList friendList);
}
