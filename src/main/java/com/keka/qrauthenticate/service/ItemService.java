package com.keka.qrauthenticate.service;

import com.keka.qrauthenticate.domain.Item;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ItemService {
    Item save(Item item) throws Exception;
    Page<Item> findAll(int page, int size, String sort, String sortDirection);
    Item findById(UUID id) throws Exception;
    Item update(UUID id, Item item) throws Exception;
    void delete(UUID id);
    void scanQcode(UUID id) throws Exception;
}
