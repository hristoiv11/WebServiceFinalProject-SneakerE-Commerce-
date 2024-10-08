package com.laboneproject.apigateway.presentationlayer.inventory;

import com.laboneproject.apigateway.businesslayer.inventory.InventoryItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(

            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<InventoryItemResponseDTO>> getAllInventory() {
        return ResponseEntity.ok().body(inventoryItemService.getAllInventories());
    }

    @GetMapping(

            value ="/{itemId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<InventoryItemResponseDTO> getInventoryItemById(@PathVariable String itemId) {
        InventoryItemResponseDTO inventoryItemResponseDTO = inventoryItemService.getInventoryItemByInventoryId(itemId);

        return ResponseEntity.status(HttpStatus.OK).body(inventoryItemResponseDTO);
    }



    @PostMapping(

            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<InventoryItemResponseDTO> addInventoryItem(@RequestBody InventoryItemRequestDTO inventoryRequestDTO) {
        InventoryItemResponseDTO addedInventoryItem = inventoryItemService.addInventoryItem(inventoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedInventoryItem);
    }

    @PutMapping(
            value ="/{itemId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
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

    @DeleteMapping(
            value = "/{itemId}",
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable String itemId) {
        inventoryItemService.deleteInventoryItem(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
