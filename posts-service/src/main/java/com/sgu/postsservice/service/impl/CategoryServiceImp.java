package com.sgu.postsservice.service.impl;

import com.google.gson.Gson;
import com.sgu.postsservice.constant.Constant;
import com.sgu.postsservice.dto.request.CategoryRequest;
import com.sgu.postsservice.dto.request.DeleteCategory;
import com.sgu.postsservice.dto.response.HttpResponseObject;
import com.sgu.postsservice.dto.response.Pagination;
import com.sgu.postsservice.exception.*;
import com.sgu.postsservice.model.Category;
import com.sgu.postsservice.repository.CategoryRepository;
import com.sgu.postsservice.service.CategoryService;
import com.sgu.postsservice.service.CloudinaryService;
import com.sgu.postsservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Override
    public HttpResponseObject getAll() {
        List<Category> categoryList = categoryRepository.findAll();

        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .data(categoryList)
                .message(Arrays.asList(Constant.SUCCESS))
                .build();
        return httpResponseObject;
    }

    @Override
    public HttpResponseObject getAllWithPagiantion(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<Category> categoryList = categoryPage.getContent();
        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .total_page(categoryPage.getTotalPages())
                .total_size(categoryPage.getTotalElements())
                .build();

        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(Constant.SUCCESS))
                .pagination(pagination)
                .data(categoryList)
                .build();

        return httpResponseObject;
    }

    @Override
    public HttpResponseObject getById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()){
            throw new NotFoundException("Can't find category with id = " + id);
        }


        HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message(Arrays.asList(Constant.SUCCESS))
                .data(Arrays.asList(categoryOptional.get()))
                .build();

        return httpResponseObject;
    }

    @Override
    public HttpResponseObject create(CategoryRequest categoryRequest) {
        try{
            //check category name Exists
            Optional<Category> categoryOptional = categoryRepository.findByName(categoryRequest.getName());
            if(categoryOptional.isPresent()){
                throw new CategoryExistsException("Tên danh mục đã tồn tại");
            }

            String now = DateUtils.getNow();
            Category category = Category.builder()
                    .name(categoryRequest.getName())
                    .url(categoryRequest.getUrl())
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            Category saveCategory = categoryRepository.save(category);
            HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                    .code(HttpStatus.OK.value())
                    .message(Arrays.asList(Constant.SUCCESS))
                    .data(Arrays.asList(saveCategory))
                    .build();

            return httpResponseObject;
        }catch (CategoryExistsException e) {
            throw new CategoryExistsException(e.getMessage());
        }
        catch (Exception ex){
            throw new ServerInternalException(ex.getMessage());
        }
    }

    @Override
    public HttpResponseObject update(Long id, CategoryRequest categoryRequest) {
        try{
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if(categoryOptional.isEmpty()){
                throw new NotFoundException("Can't find category with id = " + id);
            }

            Category category = categoryOptional.get();
            category.setName(categoryRequest.getName());
            category.setUrl(categoryRequest.getUrl());
            category.setUpdatedAt(DateUtils.getNow());

            category = categoryRepository.save(category);
            HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                    .code(HttpStatus.OK.value())
                    .message(Arrays.asList(Constant.SUCCESS))
                    .data(Arrays.asList(category))
                    .build();

            return httpResponseObject;
        }catch (Exception ex){
            throw new ServerInternalException(ex.getMessage());
        }
    }

    @Override
    public HttpResponseObject delete(DeleteCategory deleteCategory) {
        return null;
    }

    @Override
    public HttpResponseObject uploadImage(MultipartFile file) {
        String contentType = file.getContentType();
        if(!contentType.equals("image/jpeg") && !contentType.equals("image/png")){
            throw new BadRequestException("Image only support 'jpg','jpeg' and 'png'");
        }

        //Upload
        try{

            Map<?,?> map = cloudinaryService.upload(file);
            String url = (String) map.get("url");

            HttpResponseObject httpResponseObject = HttpResponseObject.builder()
                    .code(HttpStatus.OK.value())
                    .message(Arrays.asList(Constant.SUCCESS))
                    .data(Arrays.asList(url))
                    .build();
            return httpResponseObject;

        }catch (InternalServerException e) {
            throw new RuntimeException(e);
        }
    }
}
