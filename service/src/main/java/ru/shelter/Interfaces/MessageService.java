package ru.shelter.Interfaces;


import ru.shelter.dto.request.MessageCreateRequestDto;
import ru.shelter.dto.response.MessageResponseDto;


public interface MessageService extends ServiceInterface<MessageCreateRequestDto, MessageResponseDto, Long>{
}
