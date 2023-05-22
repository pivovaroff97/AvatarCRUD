package ru.pivovarov.AvatarCRUD.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.pivovarov.AvatarCRUD.handler.ResponseHandler;
import ru.pivovarov.AvatarCRUD.handler.UserStatusData;

@ControllerAdvice
public class UserExceptionGlobalHandler {

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(IllegalArgumentException exception) {
        return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, null);
    }
}
