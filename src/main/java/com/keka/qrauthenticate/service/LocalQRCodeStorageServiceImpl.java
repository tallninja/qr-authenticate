package com.keka.qrauthenticate.service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.keka.qrauthenticate.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

@Slf4j
@Service
public class LocalQRCodeStorageServiceImpl implements QRCodeStorageService {
    private final AppConfig appConfig;

    public LocalQRCodeStorageServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public String save(BufferedImage qrCode, String fileName) throws Exception {
        File outFile = getOutFile(parseFileName(fileName));
        if (!outFile.exists()) {
            ImageIO.write(qrCode, appConfig.getQrCodeFormat(), outFile);
            log.info("Saved QR code " + fileName + " to " + outFile);
        }
        return outFile.toString();
    }

    @Override
    public String save(BufferedImage qrCode) throws Exception {
        String fileName = parseFileName(extractQrCodeContents(qrCode));
        File outFile = getOutFile(fileName);
        if (!outFile.exists()) {
            ImageIO.write(qrCode, appConfig.getQrCodeFormat(), outFile);
            log.info("Saved QR code " + fileName + " to " + outFile);
        }
        return outFile.toString();
    }

    @Override
    public void delete(String fileName) throws Exception {
        File outFile = new File(fileName);
        if (outFile.exists()) {
            outFile.delete();
            log.info("Deleted QR code " + outFile);
        } else {
            log.error("QR code " + outFile + " was not found");
        }
    }

    private String extractQrCodeContents(BufferedImage qrCodeImage) throws Exception {
        BinaryBitmap binaryBitmap = convertImageToBinaryBitMap(qrCodeImage);
        Result result = new QRCodeReader().decode(binaryBitmap);
        return result.getText();
    }

    private BinaryBitmap convertImageToBinaryBitMap(BufferedImage image) {
        return new BinaryBitmap(
                new HybridBinarizer(
                        new BufferedImageLuminanceSource(image)
                )
        );
    }

    // parse file name
    // remove file extension if provided
    private String parseFileName(String fileName) {
        return fileName.split("\\.")[0] + "." + appConfig.getQrCodeFormat();
    }

    private File getOutFile(String fileName) {
        String currentDirectory = System.getProperty("user.dir");
        File qrCodesDir = new File(currentDirectory, appConfig.getQrCodeDirectory());
        if (!qrCodesDir.exists()) {
            qrCodesDir.mkdir();
            log.info("Created QR codes directory at " + qrCodesDir);
        }
        Path filePath = Path.of(qrCodesDir.getPath(), fileName);
        return filePath.toFile();
    }
}
