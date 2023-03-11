package com.sgu.userservice.dto.response;

import com.sgu.userservice.model.Pagination;
import com.sgu.userservice.utils.DateUtils;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResponseEntity {
    private int code;
    private String message;
    private List<?> data;
    @Builder.Default
    private String time = DateUtils.getNowWithFormat();
    private Pagination pagination;

}
