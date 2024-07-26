package br.com.postech.fiap.telemedicine.exceptions;

import br.com.postech.fiap.telemedicine.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandledExceptionHandler {

    @ExceptionHandler(HandledException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse> handleValidationExceptions(HandledException ex) {
        return new ResponseEntity<>(new ApiResponse(ex.getCause(), ex.getMessage(), false), HttpStatus.OK);
    }

}
