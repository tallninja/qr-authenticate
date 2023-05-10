package com.keka.qrauthenticate.service;

import com.keka.qrauthenticate.domain.Product;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {
    Product save(Product product) throws Exception;
    Page<Product> findAll(int page, int size, String sort, String sortDirection);
    Product findById(UUID id) throws Exception;
    Product update(UUID id, Product product) throws Exception;
    void delete(UUID id);
}
