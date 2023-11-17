package com.openclassrooms.mddapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDto {
    private Long id;
    @Size(max = 50)
    @Email
    @NotNull
    private String email;
    @Size(max = 20)
    @NotNull
    private String firstName;
    private boolean admin;
    @JsonIgnore
    @Size(max = 120)
    private String password;
    @NotNull
    private Set<SubjectDto> subjects = new HashSet<>();
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
