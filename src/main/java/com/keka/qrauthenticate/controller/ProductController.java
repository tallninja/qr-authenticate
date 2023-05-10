package com.keka.qrauthenticate.controller;

import com.keka.qrauthenticate.domain.Product;
import com.keka.qrauthenticate.dto.CreateProductDto;
import com.keka.qrauthenticate.dto.UpdateProductDto;
import com.keka.qrauthenticate.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> findAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) {
        Page<Product> products = productService.findAll(page, size, sort, sortDirection);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable UUID id) throws Exception {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product>  createProduct(
            HttpServletRequest request,
            @Valid @RequestBody CreateProductDto createProductDto
    ) throws Exception {
        Product product = Product.builder()
                .title(createProductDto.getTitle())
                .description(createProductDto.getDescription())
                .build();
        Product savedProduct = productService.save(product);
        URI location = new URI(request.getRequestURL() + "/" + savedProduct.getId());
        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody UpdateProductDto updateProductDto) throws Exception {
        Product product = Product.builder()
                .title(updateProductDto.getTitle())
                .description(updateProductDto.getDescription())
                .build();
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.delete(id);
    }

}
