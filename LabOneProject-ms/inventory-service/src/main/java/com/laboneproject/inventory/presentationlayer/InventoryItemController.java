package com.laboneproject.inventory.presentationlayer;


import com.laboneproject.inventory.businesslayer.InventoryItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("api/v1/inventories")
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @Autowired
    public InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService= inventoryItemService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<InventoryItemResponseDTO>>getAllInventory() {
        return ResponseEntity.ok().body(inventoryItemService.getAllInventoryItems());
    }

    @GetMapping(value ="/{itemId}",produces = "application/json")
    public ResponseEntity<InventoryItemResponseDTO> getInventoryItemById(@PathVariable String itemId) {
        InventoryItemResponseDTO inventoryItemResponseDTO = inventoryItemService.getInventoryItemById(itemId);
        /*
        if (inventoryItemResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }

         */
        return ResponseEntity.status(HttpStatus.OK).body(inventoryItemResponseDTO);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<InventoryItemResponseDTO> addInventoryItem(@RequestBody InventoryItemRequestDTO inventoryRequestDTO) {
        InventoryItemResponseDTO addedInventoryItem = inventoryItemService.addInventoryItem(inventoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedInventoryItem);
    }

    @PutMapping(value ="/{itemId}",produces = "application/json")
    public ResponseEntity<InventoryItemResponseDTO> updateInventory(
            @RequestBody InventoryItemRequestDTO inventoryItemRequestDTO,
            @PathVariable String itemId) {
        InventoryItemResponseDTO updatedInventoryItem = inventoryItemService.updateInventoryItem(inventoryItemRequestDTO, itemId);
        /*
        if (updatedInventoryItem == null) {
            return ResponseEntity.notFound().build();
        }

         */
        return ResponseEntity.status(HttpStatus.OK).body(updatedInventoryItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable String itemId) {
        inventoryItemService.deleteInventoryItem(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}


