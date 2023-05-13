package com.keka.qrauthenticate;

import com.keka.qrauthenticate.config.AppConfig;
import com.keka.qrauthenticate.repository.ItemRepository;
import com.keka.qrauthenticate.service.QRCodeGenerationService;
import com.keka.qrauthenticate.service.QRCodeStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class GenerateTestQrCodes implements CommandLineRunner {

    @Autowired
    private QRCodeGenerationService qrCodeGenerationService;

    @Autowired
    private QRCodeStorageService qrCodeStorageService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AppConfig appConfig;

    @Override
    public void run(String... args) throws Exception {
        deleteQrCodesFolder();
        itemRepository.deleteAll();

        String id1 = UUID.randomUUID().toString();
        String id2 = UUID.randomUUID().toString();
        String id3 = UUID.randomUUID().toString();
        String id4 = UUID.randomUUID().toString();

        BufferedImage qrCode1 = qrCodeGenerationService.generateQrCode(id1);
        qrCodeStorageService.save(qrCode1, "qr-code-1");
        BufferedImage qrCode2 = qrCodeGenerationService.generateQrCode(id2);
        qrCodeStorageService.save(qrCode2, "qr-code-2");
        BufferedImage qrCode3 = qrCodeGenerationService.generateQrCode(id3);
        qrCodeStorageService.save(qrCode3);
        BufferedImage qrCode4 = qrCodeGenerationService.generateQrCode(id4);
        qrCodeStorageService.save(qrCode4);
    }

    private void deleteQrCodesFolder() {
        String currWorkDir = System.getProperty("user.dir");
        File qrCodesDirectory = new File(currWorkDir, appConfig.getQrCodeDirectory());
        if (qrCodesDirectory.exists()) {
            deleteDirectory(qrCodesDirectory);
            log.info("Deleted directory " + qrCodesDirectory);
        }
    }

    private void deleteDirectory(File directory) {
        for (File subFile : Objects.requireNonNull(directory.listFiles())) {
            if (subFile.isDirectory()) {
                deleteDirectory(subFile);
                log.info("Deleted sub-directory " + subFile);
            }
            subFile.delete();
            log.info("Deleted sub-file " + subFile);
        }
    }
}
