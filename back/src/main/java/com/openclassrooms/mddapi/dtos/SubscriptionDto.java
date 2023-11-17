package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SubscriptionDto {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long subjectId;
}
