package com.laboneproject.inventory.presentationlayer;

import com.laboneproject.inventory.businesslayer.InventoryItemService;
import com.laboneproject.inventory.utils.exceptions.InUseException;
import com.laboneproject.inventory.utils.exceptions.InvalidInputException;
import com.laboneproject.inventory.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InventoryItemController.class)
@AutoConfigureMockMvc
class InventoryItemControllerUnitTest {

    private final String FOUND_INVENTORY_ID = "012";

    //private final String NOT_FOUND_INVENTORY_ID = "042";

    //private final String INVALID_INVENTORY_ID = "000";

    //private final String URI_INVENTORIES = "/api/v1/inventories";

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    InventoryItemController inventoryItemController;

    @MockBean
    private InventoryItemService inventoryItemService;


    @Test
    public void whenInventoryDoNotExists_ReturnEmptyList(){

        //arrange

        when(inventoryItemService.getAllInventoryItems()).thenReturn(Collections.emptyList());

        //act
        ResponseEntity<List<InventoryItemResponseDTO>> responseEntity = inventoryItemController.getAllInventory();

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(inventoryItemService, times(1)).getAllInventoryItems();
    }

    @Test
    public void whenInventoryExists_thenReturnInventory(){
        //arrange

        InventoryItemRequestDTO inventoryItemRequestDTO= InventoryItemRequestDTO.builder().build();
        InventoryItemResponseDTO inventoryItemResponseDTO= InventoryItemResponseDTO.builder().build();

        when(inventoryItemService.addInventoryItem(inventoryItemRequestDTO)).thenReturn(inventoryItemResponseDTO);

        //act
        ResponseEntity<InventoryItemResponseDTO> responseEntity= inventoryItemController.addInventoryItem(inventoryItemRequestDTO);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(inventoryItemResponseDTO, responseEntity.getBody());
        verify(inventoryItemService, times(1)).addInventoryItem(inventoryItemRequestDTO);
    }

    @Test
    public void whenInventoryExist_thenReturnUpdatedInventory() throws NotFoundException {

        InventoryItemRequestDTO inventoryItemRequestDTO = new InventoryItemRequestDTO();
        InventoryItemResponseDTO inventoryItemResponseDTO = InventoryItemResponseDTO.builder().inventoryId(FOUND_INVENTORY_ID).build();

        when(inventoryItemService.updateInventoryItem(inventoryItemRequestDTO, FOUND_INVENTORY_ID)).thenReturn(inventoryItemResponseDTO);

        // Act
        ResponseEntity<InventoryItemResponseDTO> responseEntity = inventoryItemController.updateInventory(inventoryItemRequestDTO, FOUND_INVENTORY_ID);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(FOUND_INVENTORY_ID, responseEntity.getBody().getInventoryId());
        verify(inventoryItemService, times(1)).updateInventoryItem(inventoryItemRequestDTO, FOUND_INVENTORY_ID);

    }

    @Test
    public void whenDeleteInventory_thenStatusNoContent() throws Exception {
        doNothing().when(inventoryItemService).deleteInventoryItem(FOUND_INVENTORY_ID);

        mockMvc.perform(delete("/api/v1/inventories/{inventoryId}", FOUND_INVENTORY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenGetNonExistentInventory_thenThrowNotFoundException() throws Exception {
        String nonExistentId = "050";
        when(inventoryItemService.getInventoryItemById(nonExistentId))
                .thenThrow(new NotFoundException("Inventory not found"));

        mockMvc.perform(get("/inventories/{id}", nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenAddInventoryWithInvalidInput_thenThrowInvalidInputException() {
        InventoryItemRequestDTO invalidInventoryItemRequestDTO = new InventoryItemRequestDTO("2","3");
        doThrow(new InvalidInputException("Invalid input")).when(inventoryItemService).addInventoryItem(invalidInventoryItemRequestDTO);

        assertThrows(InvalidInputException.class, () -> inventoryItemController.addInventoryItem(invalidInventoryItemRequestDTO));
    }

    @Test
    public void whenDeleteInventoryInUse_thenThrowInUseException() {
        String inUseInventoryId = "012";
        doThrow(new InUseException("Inventory is currently in use")).when(inventoryItemService).deleteInventoryItem(inUseInventoryId);

        assertThrows(InUseException.class, () -> inventoryItemController.deleteInventoryItem(inUseInventoryId));
    }

}