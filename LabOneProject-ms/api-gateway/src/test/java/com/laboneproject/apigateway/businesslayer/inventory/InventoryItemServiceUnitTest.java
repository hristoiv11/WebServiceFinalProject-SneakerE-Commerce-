package com.laboneproject.apigateway.businesslayer.inventory;

import com.laboneproject.apigateway.businesslayer.customers.CustomerServiceImpl;
import com.laboneproject.apigateway.dataaccesslayer.customers.CustomersServiceClient;
import com.laboneproject.apigateway.dataaccesslayer.inventory.InventoryItemServiceClient;
import com.laboneproject.apigateway.datamapperlayer.customers.CustomerResponseMapper;
import com.laboneproject.apigateway.datamapperlayer.inventory.InventoryItemResponseMapper;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryItemServiceUnitTest {

    @Mock
    private InventoryItemServiceClient inventoryItemServiceClient;

    @Mock
    private InventoryItemResponseMapper inventoryItemResponseMapper;

    @InjectMocks
    private InventoryItemItemServiceImpl inventoryItemItemService;

    @Test
    void testGetAllInventories() {
        // Setup mocks for dependencies
        when(inventoryItemServiceClient.getAllInventories()).thenReturn(Arrays.asList(new InventoryItemResponseDTO()));
        when(inventoryItemResponseMapper.responseModelListToReposeList(any())).thenReturn(Arrays.asList(new InventoryItemResponseDTO()));

        // Call the method under test
        List<InventoryItemResponseDTO> result = inventoryItemItemService.getAllInventories();

        // Verify the results and interactions
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(inventoryItemServiceClient).getAllInventories();
        verify(inventoryItemResponseMapper).responseModelListToReposeList(any());
    }
}
