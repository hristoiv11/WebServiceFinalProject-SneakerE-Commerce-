package com.laboneproject.apigateway.businesslayer.inventory;

import com.laboneproject.apigateway.dataaccesslayer.inventory.InventoryItemServiceClient;
import com.laboneproject.apigateway.datamapperlayer.inventory.InventoryItemResponseMapper;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemRequestDTO;
import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryItemItemServiceImpl implements InventoryItemService {

    private final InventoryItemServiceClient inventoryItemServiceClient;
    private final InventoryItemResponseMapper inventoryItemResponseMapper;

    public InventoryItemItemServiceImpl(InventoryItemServiceClient inventoryItemServiceClient, InventoryItemResponseMapper inventoryItemResponseMapper) {
        this.inventoryItemServiceClient = inventoryItemServiceClient;
        this.inventoryItemResponseMapper = inventoryItemResponseMapper;
    }

    @Override
    public List<InventoryItemResponseDTO> getAllInventories() {
        return inventoryItemResponseMapper.responseModelListToReposeList(inventoryItemServiceClient.getAllInventories());
    }

    @Override
    public InventoryItemResponseDTO getInventoryItemByInventoryId(String inventoryId) {
        return inventoryItemResponseMapper.responseModelToResponseModel(inventoryItemServiceClient.getInventoryByInventoryId(inventoryId));
    }

    @Override
    public InventoryItemResponseDTO addInventoryItem(InventoryItemRequestDTO inventoryItemRequestDTO) {
        return inventoryItemResponseMapper.responseModelToResponseModel(inventoryItemServiceClient.addInventoryItem(inventoryItemRequestDTO));
    }

    @Override
    public InventoryItemResponseDTO updateInventoryItem(InventoryItemRequestDTO inventoryItemRequestDTO, String inventoryId) {
        return inventoryItemResponseMapper.responseModelToResponseModel(inventoryItemServiceClient.updateInventoryItem(inventoryId,inventoryItemRequestDTO));
    }

    @Override
    public void deleteInventoryItem(String inventoryId) {
        inventoryItemServiceClient.deleteInventoryItem(inventoryId);
    }
}
