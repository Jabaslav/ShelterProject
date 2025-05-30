package ru.shelter.dto.response;

import ru.shelter.model.ChatMember;


public record ChatMemberResponse(
    //

    Long chatId,
    Long userId,
    ChatMember.MemberRole role,
    Boolean isActive

){}
