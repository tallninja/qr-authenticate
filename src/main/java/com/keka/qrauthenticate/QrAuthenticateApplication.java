package com.keka.qrauthenticate;

import com.keka.qrauthenticate.config.AppConfig;
import com.keka.qrauthenticate.service.QRCodeGenerationService;
import com.keka.qrauthenticate.service.QRCodeStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
public class QrAuthenticateApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrAuthenticateApplication.class, args);
	}

}
