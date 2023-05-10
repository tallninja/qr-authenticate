package com.keka.qrauthenticate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductDto {
    @NotBlank
    private String title;

    private String description;
}
