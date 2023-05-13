package com.keka.qrauthenticate.domain;

import com.keka.qrauthenticate.service.QRCodeStorageService;
import jakarta.persistence.PostRemove;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemAuditListener {

    private final QRCodeStorageService qrCodeStorageService;

    public ItemAuditListener(QRCodeStorageService qrCodeStorageService) {
        this.qrCodeStorageService = qrCodeStorageService;
    }

    @PostRemove
    private void deleteQrCodeFromStorage(Item item) throws Exception {
        qrCodeStorageService.delete(item.getQcode());
        log.info("Deleted product " + item.getId());
    }
}
