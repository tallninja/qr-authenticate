package com.keka.qrauthenticate.repository;

import com.keka.qrauthenticate.domain.Item;
import com.keka.qrauthenticate.domain.Scan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScanRepository extends JpaRepository<Scan, UUID> {
    int findByItem(Item item);
}
