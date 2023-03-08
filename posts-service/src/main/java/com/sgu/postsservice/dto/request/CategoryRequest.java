package com.sgu.postsservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NonNull
    @NotBlank(message = "Tên danh mục không thể rỗng")
    @Length(min = 5,message = "Tên danh mục tối thiểu 5 ký tự")
    private String name;
    @NonNull
    private String url;
}
