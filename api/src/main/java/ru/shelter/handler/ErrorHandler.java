package ru.shelter.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shelter.exception.NotFoundException;
import ru.shelter.exception.ValidationException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationError(final ValidationException e) {
        log.warn("ERROR[400]: Произошла ошибка ValidationException: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse constraintViolationError(final ConstraintViolationException e) {
        log.warn("ERROR[400]: Произошла ошибка ConstraintViolationException: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

//    @ExceptionHandler(BadRequestException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse constraintViolationError(final BadRequestException e) {
//        log.warn("ERROR[400]: Произошла ошибка BadRequestException: {}", e.getMessage());
//        return new ErrorResponse(e.getMessage());
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("ERROR[400]: MethodArgumentNotValidException: {}", e.getMessage());

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ErrorResponse(String.join(", ", errors));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundError(final NotFoundException e) {
        log.info("ERROR[404]: Произошла ошибка NotFoundException: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionError(final Exception e) {
        log.error("ERROR[500]: Произошла ошибка Exception: {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse databaseError(final Exception e) {

        String message ="Database error. ";

        if (e.getCause() instanceof ConstraintViolationException)
        {
            message += "Duplicate entry: " + extractConstraintName(e.getCause());
        }

        log.error("ERROR[500]: Произошла ошибка Exception: {}", e.getMessage(), e);
        return new ErrorResponse(message);
    }
// TODO не получает сообщения о конкретном ограничении бд
    private String extractConstraintName(Throwable e) {
        // Пример для Hibernate
        if (e instanceof ConstraintViolationException cve) {
            return cve.getConstraintViolations().toString(); // Например, "uk_user_email"
        }
        return "unknown";
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse elementNotFoundError(final Exception e)
    {
        log.error("ERROR[404]: Запрошенный ресурс не найден: {}", e.getMessage(), e);
        return new  ErrorResponse(e.getMessage());
    }
}
