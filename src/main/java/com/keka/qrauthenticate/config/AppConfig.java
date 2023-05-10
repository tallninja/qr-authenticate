package com.keka.qrauthenticate.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfig {

    @Value("${qr.code.format}")
    private String qrCodeFormat;

    @Value("${qr.code.directory}")
    private String qrCodeDirectory;

    @Value("${qr.code.width}")
    private Integer qrCodeWidth;

    @Value("${qr.code.height}")
    private Integer qrCodeHeight;
}
