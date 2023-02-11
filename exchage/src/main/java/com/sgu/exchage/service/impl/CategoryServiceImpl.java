package com.sgu.exchage.service.impl;

import com.sgu.exchage.dto.response.CategoryDto;
import com.sgu.exchage.exception.NotFoundException;
import com.sgu.exchage.model.Category;
import com.sgu.exchage.repository.CategoryRepository;
import com.sgu.exchage.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category %s is not found", id)));
        return modelMapper.map(category, CategoryDto.class);
    }

}
