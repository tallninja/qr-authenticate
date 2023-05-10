package com.keka.qrauthenticate.repository;

import com.keka.qrauthenticate.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
