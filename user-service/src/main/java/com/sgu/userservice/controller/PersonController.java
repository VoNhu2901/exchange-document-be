package com.sgu.userservice.controller;

import com.sgu.userservice.dto.request.PersonRequest;
import com.sgu.userservice.dto.response.HttpResponseObject;
import com.sgu.userservice.service.AccountService;
import com.sgu.userservice.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/get-all-person")
    public ResponseEntity<HttpResponseObject> getAllAccount(){
        HttpResponseObject httpResponseObject = personService.getAllPerson();

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @GetMapping("/get-all-person-with-pagination")
    public ResponseEntity<HttpResponseObject> getAllAccountWithPagination(
            @RequestParam(name = "page", required = true) int page,
            @RequestParam(name = "size", required = true) int size
    ){
        HttpResponseObject httpResponseObject = personService.getAllPersonWithPagination(page,size);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @GetMapping("/get-person-by-id/{id}")
    public ResponseEntity<HttpResponseObject> getAccountByPersonId(
            @PathVariable(name = "id") Long personId
    ){
        HttpResponseObject httpResponseObject = personService.getAccoutByPersonId(personId);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<HttpResponseObject> updatePerson(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid PersonRequest personRequest
    ){
        HttpResponseObject httpResponseObject = personService.updatePerson(id,personRequest);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }
}
