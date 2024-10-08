package com.laboneproject.apigateway.businesslayer.inventory;

import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemRequestDTO;
import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemResponseDTO;

import java.util.List;

public interface InventoryItemService {

    List<InventoryItemResponseDTO> getAllInventories();
    InventoryItemResponseDTO getInventoryItemByInventoryId(String inventoryId);
    InventoryItemResponseDTO addInventoryItem(InventoryItemRequestDTO inventoryItemRequestDTO);
    InventoryItemResponseDTO updateInventoryItem(InventoryItemRequestDTO inventoryItemRequestDTO, String inventoryId);
    void deleteInventoryItem(String inventoryId);
}
