package com.keka.qrauthenticate.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.keka.qrauthenticate.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Slf4j
@Service
public class QRCodeGenerationServiceImpl implements QRCodeGenerationService {

    private final AppConfig appConfig;

    public QRCodeGenerationServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public BufferedImage generateQrCode(String qrCodeText) throws WriterException {
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(
                qrCodeText, BarcodeFormat.QR_CODE, appConfig.getQrCodeWidth(), appConfig.getQrCodeHeight()
        );
        log.info("Generated QR code with contents " + qrCodeText);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
