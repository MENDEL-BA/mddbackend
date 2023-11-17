package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PostDto {
    private Long id;
    @Size(max = 200)
    @NotBlank
    private String title;
    @Size(max = 5000)
    @NotBlank
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotNull
    private Long authorId;
    @NotNull
    private String authorFirstName;
    @NotNull
    private Long subjectId;
    private String subject;
    List<MessageDto> messages;
}
