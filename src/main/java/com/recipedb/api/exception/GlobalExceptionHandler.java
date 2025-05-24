package com.recipedb.api.exception;

import com.recipedb.api.constants.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidFieldException.class})
    public ResponseEntity<Object> invalidFieldException(InvalidFieldException exception) {
        Map<String, String> errorDetails = Map.of(
                "field", exception.getFieldName(),
                "message", exception.getMessage());
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> badCredentialsException(BadCredentialsException exception) {
        return ResponseEntity.badRequest().body(ErrorMessages.INVALID_CREDENTIALS);
    }

    @ExceptionHandler({UsernameAlreadyExistException.class})
    public ResponseEntity<Object> usernameAlreadyExistsException(UsernameAlreadyExistException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> runtimeException(RuntimeException exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
