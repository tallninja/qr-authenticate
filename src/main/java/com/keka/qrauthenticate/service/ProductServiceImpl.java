package com.keka.qrauthenticate.service;

import com.keka.qrauthenticate.domain.Product;
import com.keka.qrauthenticate.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final QRCodeGenerationService qrCodeGenerationService;
    private final QRCodeStorageService qrCodeStorageService;

    public ProductServiceImpl(
            ProductRepository productRepository,
            QRCodeGenerationService qrCodeGenerationService,
            QRCodeStorageService qrCodeStorageService
    ) {
        this.productRepository = productRepository;
        this.qrCodeGenerationService = qrCodeGenerationService;
        this.qrCodeStorageService = qrCodeStorageService;
    }

    @Override
    public Product save(Product product) throws Exception {
        Product _product = productRepository.save(product);
        BufferedImage qrCode = qrCodeGenerationService
                .generateQrCode(_product.getId().toString());
        String outFile = qrCodeStorageService.save(qrCode).toString();
        _product.setQrCode(outFile);
        return productRepository.save(_product);
    }

    @Override
    public Page<Product> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product findById(UUID id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> {
           log.error("Product id " + id + " was not found.");
           return new Exception("Product id " + id + " was not found.");
        });
    }

    @Override
    public Product update(UUID id, Product product) throws Exception {
        Product _product = this.findById(id);
        _product.setTitle(product.getTitle());
        _product.setDescription(product.getDescription());
        return productRepository.save(_product);
    }

    @Override
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
