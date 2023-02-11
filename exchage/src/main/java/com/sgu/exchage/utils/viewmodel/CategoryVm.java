package com.sgu.exchage.utils.viewmodel;

import com.sgu.exchage.model.Category;

public record CategoryVm(Long id, String name) {
    public static CategoryVm fromModel(Category category){
        return new CategoryVm(category.getId(), category.getName());
    }
}
