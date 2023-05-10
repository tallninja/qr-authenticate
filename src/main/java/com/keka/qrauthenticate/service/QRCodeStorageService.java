package com.keka.qrauthenticate.service;

import java.awt.image.BufferedImage;
import java.io.File;

public interface QRCodeStorageService {
    String save(BufferedImage qrCode, String fileName) throws Exception;
    String save(BufferedImage qrCode) throws Exception;
    void delete(String fileName) throws Exception;
}
