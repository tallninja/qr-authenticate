package com.keka.qrauthenticate.service;

import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;

public interface QRCodeGenerationService {
    BufferedImage generateQrCode(String qrCodeText) throws WriterException;
}
