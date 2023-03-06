package com.sgu.authservice.dto.response;

import com.sgu.authservice.model.Pagination;
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
    private List<String> message;
    private List<?> data;
    private final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private Pagination pagination;

    public HttpResponseObject(int code, List<String> message, List<?> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
