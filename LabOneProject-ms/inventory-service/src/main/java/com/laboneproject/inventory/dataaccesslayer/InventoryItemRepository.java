package com.laboneproject.inventory.dataaccesslayer;


import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryItemRepository extends JpaRepository<InventoryItem,Integer> {

    InventoryItem findInventoryItemByInventoryItemIdentifier_InventoryId(String inventoryId);
}


