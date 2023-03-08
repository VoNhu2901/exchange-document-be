package com.sgu.postsservice.controller;

import com.sgu.postsservice.dto.request.CategoryRequest;
import com.sgu.postsservice.dto.request.DeleteCategory;
import com.sgu.postsservice.dto.response.HttpResponseObject;
import com.sgu.postsservice.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/get-all")
    public ResponseEntity<HttpResponseObject> getAllAccount(){
        HttpResponseObject httpResponseObject = categoryService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @GetMapping("/get-all-with-pagination")
    public ResponseEntity<HttpResponseObject> getAllAccountWithPagination(
            @RequestParam(name = "page", required = true) int page,
            @RequestParam(name = "size", required = true) int size
    ){
        HttpResponseObject httpResponseObject = categoryService.getAllWithPagiantion(page,size);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<HttpResponseObject> getAllAccountWithPagination(
            @PathVariable(name = "id") Long id
    ){
        HttpResponseObject httpResponseObject = categoryService.getById(id);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponseObject> create(
            @RequestBody @Valid CategoryRequest categoryRequest
    ){
        HttpResponseObject httpResponseObject = categoryService.create(categoryRequest);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<HttpResponseObject> updateById(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid CategoryRequest categoryRequest
    ){
        HttpResponseObject httpResponseObject = categoryService.update(id,categoryRequest);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<HttpResponseObject> updateById(
            @RequestParam(name = "file") MultipartFile file
            ){
        HttpResponseObject httpResponseObject = categoryService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<HttpResponseObject> create(
//            @RequestBody @Valid DeleteCategory deleteCategory
//    ){
//        HttpResponseObject httpResponseObject = categoryService.delete(deleteCategory);
//
//        return ResponseEntity.status(HttpStatus.OK).body(httpResponseObject);
//    }
}
