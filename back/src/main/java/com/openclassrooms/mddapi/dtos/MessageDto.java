package com.openclassrooms.mddapi.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MessageDto {
    private Long id;
    private LocalDateTime createdAt;
    @NotBlank
    @Size(max = 500)
    private String message;
    private String authorFirstName;
    @NotNull
    private Long authorId;
    @NotNull
    private Long postId;
}
