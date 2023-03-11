package com.sgu.userservice.exception;

import com.sgu.userservice.dto.response.HttpResponseEntity;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@RestControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseEntity);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ArrayList<String> obj = (ArrayList<String>) (ex.getDetailMessageArguments())[1];

        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseEntity);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String err = ex.getCause().getCause().getMessage();
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(err)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseEntity);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String err = ex.getMessage().split(":")[0];
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(err)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseEntity);
    }


    @ExceptionHandler(value = {
            IllegalArgumentException.class})
    public ResponseEntity<HttpResponseEntity> handleIllegalArgumentException(IllegalArgumentException ex) {
        String err = ex.getCause().getCause().getMessage();
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(err)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseEntity);

    }

    @ExceptionHandler(value = {
            BadRequestException.class})
    public ResponseEntity<HttpResponseEntity> handleBadRequestException(BadRequestException ex) {
        String err = ex.getCause().getCause().getMessage();
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(err)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseEntity);

    }

    @ExceptionHandler(value = {
            MaxUploadSizeExceededException.class})
    public ResponseEntity<HttpResponseEntity> handleBadRequestException(MaxUploadSizeExceededException ex) {
        String err = ex.getCause().getCause().getMessage();
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(err)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseEntity);

    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<HttpResponseEntity> handleNotFoundException(UserNotFoundException ex) {
        String err = ex.getMessage();
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(err)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(httpResponseEntity);

    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<HttpResponseEntity> handleNotFoundException(NotFoundException ex) {
        String err = ex.getCause().getCause().getMessage();
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(err)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(httpResponseEntity);

    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<HttpResponseEntity> handleForbiddenException(ForbiddenException ex) {
        String err = ex.getMessage();
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(err)
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(httpResponseEntity);


    }

    //
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<HttpResponseEntity> handleForbiddenException(NullPointerException ex) {
        String err = ex.getCause().getCause().getMessage();
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(err)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseEntity);
    }


}
