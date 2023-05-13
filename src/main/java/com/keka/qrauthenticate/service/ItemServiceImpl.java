package com.keka.qrauthenticate.service;

import com.keka.qrauthenticate.domain.Item;
import com.keka.qrauthenticate.domain.Scan;
import com.keka.qrauthenticate.repository.ItemRepository;
import com.keka.qrauthenticate.repository.ScanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final QRCodeGenerationService qrCodeGenerationService;
    private final QRCodeStorageService qrCodeStorageService;
    private final ScanRepository scanRepository;

    public ItemServiceImpl(
            ItemRepository itemRepository,
            QRCodeGenerationService qrCodeGenerationService,
            QRCodeStorageService qrCodeStorageService,
            ScanRepository scanRepository
    ) {
        this.itemRepository = itemRepository;
        this.qrCodeGenerationService = qrCodeGenerationService;
        this.qrCodeStorageService = qrCodeStorageService;
        this.scanRepository = scanRepository;
    }

    @Override
    public Item save(Item item) throws Exception {
        Item _item = itemRepository.save(item);
        BufferedImage qrCode = qrCodeGenerationService
                .generateQrCode(_item.getId().toString());
        String outFile = qrCodeStorageService.save(qrCode);
        _item.setQcode(outFile);
        return itemRepository.save(_item);
    }

    @Override
    public Page<Item> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return itemRepository.findAll(pageRequest);
    }

    @Override
    public Item findById(UUID id) throws Exception {
        return itemRepository.findById(id).orElseThrow(() -> {
           log.error("Item id " + id + " was not found.");
           return new Exception("Item id " + id + " was not found.");
        });
    }

    @Override
    public Item update(UUID id, Item item) throws Exception {
        Item _item = this.findById(id);
        _item.setTitle(item.getTitle());
        _item.setDescription(item.getDescription());
        return itemRepository.save(_item);
    }

    @Override
    public void delete(UUID id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void scanQcode(UUID id) throws Exception {
        Item item = this.findById(id);
        scanRepository.save(Scan.builder().item(item)
                .scannedAt(LocalDateTime.now()).build());
    }
}
