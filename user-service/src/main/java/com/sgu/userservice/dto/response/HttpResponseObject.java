package com.sgu.userservice.dto.response;

import com.sgu.userservice.model.Pagination;
import com.sgu.userservice.utils.DateUtils;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResponseObject {
    private int code;
    private String message;
    private List<?> data;
    @Builder.Default
    private String time = DateUtils.getNowWithFormat();
    private Pagination pagination;

}
