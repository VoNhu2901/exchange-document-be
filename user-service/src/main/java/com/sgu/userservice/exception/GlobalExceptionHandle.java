package com.sgu.userservice.exception;

import com.sgu.userservice.constant.ConstantMessage;
import com.sgu.userservice.dto.response.HttpResponseObject;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ConstantMessage.BAD_REQUEST + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ConstantMessage.BAD_REQUEST + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ConstantMessage.BAD_REQUEST + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ConstantMessage.BAD_REQUEST + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }


    @ExceptionHandler(value = {
            IllegalArgumentException.class})
    public ResponseEntity<HttpResponseObject> handleIllegalArgumentException(IllegalArgumentException ex) {
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ConstantMessage.BAD_REQUEST + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);

    }

    @ExceptionHandler(value = {
            BadRequestException.class})
    public ResponseEntity<HttpResponseObject> handleBadRequestException(BadRequestException ex) {
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ConstantMessage.BAD_REQUEST + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);

    }

    @ExceptionHandler(value = {
            MaxUploadSizeExceededException.class})
    public ResponseEntity<HttpResponseObject> handleBadRequestException(MaxUploadSizeExceededException ex) {
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ConstantMessage.BAD_REQUEST + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);

    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<HttpResponseObject> handleNotFoundException(NotFoundException ex) {
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ConstantMessage.NOT_FOUND + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(httpResponseObject);

    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<HttpResponseObject> handleForbiddenException(ForbiddenException ex) {
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(ConstantMessage.FORBIDDEN + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(httpResponseObject);

    }



}
