package com.sgu.userservice.controller;

import com.sgu.userservice.dto.request.DeleteRequest;
import com.sgu.userservice.dto.request.PersonRequest;
import com.sgu.userservice.dto.request.UserRequest;
import com.sgu.userservice.dto.response.HttpResponseObject;
import com.sgu.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<HttpResponseObject> register(@RequestBody @Valid UserRequest userRequest){
        HttpResponseObject httpResponseObject = userService.register(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(httpResponseObject);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpResponseObject> delete(@RequestBody @Valid DeleteRequest deleteRequest){
        HttpResponseObject httpResponseObject = userService.delete(deleteRequest);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }
}
