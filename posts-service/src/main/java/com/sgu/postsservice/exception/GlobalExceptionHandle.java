package com.sgu.postsservice.exception;

import com.sgu.postsservice.dto.response.HttpResponseObject;
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
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {


        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Arrays.asList(ex.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ArrayList<String> obj = (ArrayList<String>) (ex.getDetailMessageArguments())[1];

        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(obj)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String err = ex.getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String err = ex.getMessage().split(":")[0];
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }


    @ExceptionHandler(value = {
            IllegalArgumentException.class})
    public ResponseEntity<HttpResponseObject> handleIllegalArgumentException(IllegalArgumentException ex) {
        String err = ex.getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);

    }

    @ExceptionHandler(value = {
            BadRequestException.class})
    public ResponseEntity<HttpResponseObject> handleBadRequestException(BadRequestException ex) {
        String err = ex.getCause().getCause().getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);

    }

    @ExceptionHandler(value = {
            MaxUploadSizeExceededException.class})
    public ResponseEntity<HttpResponseObject> handleBadRequestException(MaxUploadSizeExceededException ex) {
        String err = ex.getCause().getCause().getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);

    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<HttpResponseObject> handleNotFoundException(NotFoundException ex) {
        String err = ex.getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(httpResponseObject);

    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<HttpResponseObject> handleForbiddenException(ForbiddenException ex) {
        String err = ex.getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(httpResponseObject);


    }

    //
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<HttpResponseObject> handleForbiddenException(NullPointerException ex) {
        String err = ex.getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }

    @ExceptionHandler(value={ServerInternalException.class})
    public ResponseEntity<HttpResponseObject> handleServerInternalException(ServerInternalException ex) {
        String err = ex.getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(httpResponseObject);
    }

    //

    @ExceptionHandler(value={MultipartException.class})
    public ResponseEntity<HttpResponseObject> handleMultipartException(MultipartException ex) {
        String err = ex.getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpResponseObject);
    }

    @ExceptionHandler(value={CategoryExistsException.class})
    public ResponseEntity<HttpResponseObject> handleCategoryExistsException(CategoryExistsException ex) {
        String err = ex.getMessage();
        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.CONFLICT.value())
                .message(Arrays.asList(err))
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(httpResponseObject);
    }



}
