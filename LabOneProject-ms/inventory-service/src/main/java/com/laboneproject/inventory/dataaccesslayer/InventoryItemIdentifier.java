package com.laboneproject.inventory.dataaccesslayer;


import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;


@Embeddable
@Getter
public class InventoryItemIdentifier {


    private String inventoryId;
    public InventoryItemIdentifier(){
        this.inventoryId= UUID.randomUUID().toString();
    }

    /*
    public InventoryItemIdentifier(String inventoryId) {
        this.inventoryId = inventoryId;
    }

     */
}


