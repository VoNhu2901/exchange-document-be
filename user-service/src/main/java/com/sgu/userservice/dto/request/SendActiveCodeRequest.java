package com.sgu.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendActiveCodeRequest {
//    @NonNull
    @NotBlank(message = "email not blank")
    private String username;
}
