package com.keka.qrauthenticate.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetProductDto {
    private UUID id;
    private String title;
    private String description;
    private String qrCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
