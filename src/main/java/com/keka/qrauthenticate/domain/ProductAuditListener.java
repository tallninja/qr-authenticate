package com.keka.qrauthenticate.domain;

import com.keka.qrauthenticate.service.QRCodeStorageService;
import jakarta.persistence.PostRemove;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductAuditListener {

    private final QRCodeStorageService qrCodeStorageService;

    public ProductAuditListener(QRCodeStorageService qrCodeStorageService) {
        this.qrCodeStorageService = qrCodeStorageService;
    }

    @PostRemove
    private void deleteQrCodeFromStorage(Product product) throws Exception {
        qrCodeStorageService.delete(product.getQrCode());
        log.info("Deleted product " + product.getId());
    }
}
