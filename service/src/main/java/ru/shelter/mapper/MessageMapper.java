package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.MessageRequest;
import ru.shelter.dto.response.MessageResponse;
import ru.shelter.model.Message;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    MessageResponse toMessageResponse (Message message);

    List<MessageResponse> toMessageResponseList (List<Message> message);

    Message fromDto (MessageRequest requestDto);


}