package com.keka.qrauthenticate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProductDto {
    @NotBlank
    private String title;

    private String description;
}
