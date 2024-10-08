package com.laboneproject.inventory.businesslayer;


import com.laboneproject.inventory.presentationlayer.InventoryItemRequestDTO;
import com.laboneproject.inventory.presentationlayer.InventoryItemResponseDTO;

import java.util.List;


public interface InventoryItemService {

    List<InventoryItemResponseDTO> getAllInventoryItems();
    InventoryItemResponseDTO getInventoryItemById(String inventoryId);
    InventoryItemResponseDTO addInventoryItem(InventoryItemRequestDTO inventoryItemRequestDTO);
    InventoryItemResponseDTO updateInventoryItem(InventoryItemRequestDTO inventoryItemRequestDTO, String inventoryId);
    void deleteInventoryItem(String inventoryId);
}


