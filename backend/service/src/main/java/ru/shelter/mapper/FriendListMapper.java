package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.FriendListRequest;
import ru.shelter.dto.response.FriendListResponse;
import ru.shelter.model.FriendList;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendListMapper {

//    @Mapping(source = "userId", target = "userId")
//    @Mapping(source = "friendId", target = "friendId")
//    @Mapping(source = "status", target = "status")
    FriendList fromDto(FriendListRequest requestDto);

//    @Mapping(source = "userId", target = "userId")
//    @Mapping(source = "friendId", target = "friendId")
//    @Mapping(source = "status", target = "status")
    FriendListResponse toDto(FriendList friendList);

    void updateFromDto(FriendListRequest requestDto, @MappingTarget FriendList friendList);
}
