package com.laboneproject.inventory.dataaccesslayer;

import com.laboneproject.inventory.presentationlayer.InventoryItemController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InventoryItemRepositoryIntegrationTest {

    private final String INVALID_INVENTORY_ID = "045";


    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @BeforeEach
    public void setupDb(){
        inventoryItemRepository.deleteAll();
    }


    @Test
    public void whenInventoryExists_thenReturnCorrectInventoryDetails(){
        //arrange
        InventoryItem inventoryItem1= new InventoryItem(new StockLevel("3","10"));

        inventoryItemRepository.save(inventoryItem1);
        //act
        InventoryItem inventoryItem= inventoryItemRepository.findInventoryItemByInventoryItemIdentifier_InventoryId(inventoryItem1.getInventoryItemIdentifier().getInventoryId());

        //assert
        assertNotNull(inventoryItem);
        assertEquals(inventoryItem.getInventoryItemIdentifier().getInventoryId(), inventoryItem1.getInventoryItemIdentifier().getInventoryId());
        assertEquals(inventoryItem.getStockLevel().getAvailableLevel(),inventoryItem1.getStockLevel().getAvailableLevel());
        assertEquals(inventoryItem.getStockLevel().getRestockLevel(),inventoryItem1.getStockLevel().getRestockLevel());

    }

    @Test
    public void whenInventoryDoesNotExists_thenReturnNull(){

        InventoryItem inventoryItem = inventoryItemRepository.findInventoryItemByInventoryItemIdentifier_InventoryId(INVALID_INVENTORY_ID);

        assertNull(inventoryItem,"Invalid inventoryId");
    }

    @Test
    public void whenFindAll_thenReturnAllInventories() {
        // Arrange
        InventoryItem inventoryItem1 = new InventoryItem(new StockLevel("12","15"));
        InventoryItem inventoryItem2 = new InventoryItem(new StockLevel("13","16"));
        inventoryItemRepository.save(inventoryItem1);
        inventoryItemRepository.save(inventoryItem2);

        // Act
        List<InventoryItem> allInventories = inventoryItemRepository.findAll();

        // Assert
        assertEquals(2, allInventories.size());
        assertTrue(allInventories.contains(inventoryItem1));
        assertTrue(allInventories.contains(inventoryItem2));
    }

}