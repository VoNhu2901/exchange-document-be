package com.sgu.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class BlockAccountRequest {
    @NonNull
    @NotBlank(message = "username can't blank")
    private String username;
    @NonNull
    @NotBlank(message = "reason for blocking can't blank")
    private String reasonForBlocking;
}
