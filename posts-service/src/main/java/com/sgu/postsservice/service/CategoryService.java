package com.sgu.postsservice.service;

import com.sgu.postsservice.dto.request.CategoryRequest;
import com.sgu.postsservice.dto.request.DeleteCategory;
import com.sgu.postsservice.dto.response.HttpResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {
    public HttpResponseObject getAll();

    public HttpResponseObject getAllWithPagiantion(int page, int size);

    public HttpResponseObject getById(Long id);

    public HttpResponseObject create(CategoryRequest categoryRequest);

    public HttpResponseObject update(Long id, CategoryRequest categoryRequest);

    public HttpResponseObject delete(DeleteCategory deleteCategory);

    public HttpResponseObject uploadImage(MultipartFile file);
}
