package com.sgu.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList("Person1","Person2"));
    }
}
