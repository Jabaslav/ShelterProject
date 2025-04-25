package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.MessageCreateRequestDto;
import ru.shelter.dto.response.MessageResponseDto;
import ru.shelter.model.Message;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    MessageResponseDto toMessageResponse (Message message);


    List<MessageResponseDto> toMessageResponseList (List<Message> message);

    Message fromDto (MessageCreateRequestDto requestDto);
}