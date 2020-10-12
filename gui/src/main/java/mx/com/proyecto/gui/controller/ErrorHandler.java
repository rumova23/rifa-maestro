package mx.com.proyecto.gui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import mx.com.proyecto.gui.exception.PacGuiProcessException;
import mx.com.proyecto.servidor.dto.ErrorInfoDto;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(PacGuiProcessException.class)
    public ResponseEntity<ErrorInfoDto> methodArgumentNotValidException(HttpServletRequest request, PacGuiProcessException e) {


        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(e.getMessage());

        ErrorInfoDto errorInfo = new ErrorInfoDto( errorMessage.toString(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);

    }

}