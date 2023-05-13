package com.keka.qrauthenticate.controller;

import com.keka.qrauthenticate.domain.Item;
import com.keka.qrauthenticate.dto.CreateItemDto;
import com.keka.qrauthenticate.dto.UpdateItemDto;
import com.keka.qrauthenticate.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Page<Item>> findAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) {
        Page<Item> items = itemService.findAll(page, size, sort, sortDirection);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findItem(@PathVariable UUID id) throws Exception {
        Item item = itemService.findById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item>  createItem(
            HttpServletRequest request,
            @Valid @RequestBody CreateItemDto createItemDto
    ) throws Exception {
        Item item = Item.builder()
                .title(createItemDto.getTitle())
                .description(createItemDto.getDescription())
                .build();
        Item savedItem = itemService.save(item);
        URI location = new URI(request.getRequestURL() + "/" + savedItem.getId());
        return ResponseEntity.created(location).body(savedItem);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable UUID id, @RequestBody UpdateItemDto updateItemDto) throws Exception {
        Item item = Item.builder()
                .title(updateItemDto.getTitle())
                .description(updateItemDto.getDescription())
                .build();
        return itemService.update(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable UUID id) {
        itemService.delete(id);
    }

    @GetMapping("/scan/{id}")
    public void scanQcode(@PathVariable UUID id) throws Exception {
        itemService.scanQcode(id);
    }

}
