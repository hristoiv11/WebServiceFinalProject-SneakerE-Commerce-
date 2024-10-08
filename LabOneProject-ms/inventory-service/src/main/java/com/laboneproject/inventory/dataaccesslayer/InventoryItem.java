package com.laboneproject.inventory.dataaccesslayer;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="inventories")
@Data
@NoArgsConstructor
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    InventoryItemIdentifier inventoryItemIdentifier;

    @Embedded
    private StockLevel stockLevel;

    public InventoryItem(@NotNull StockLevel stockLevel) {
        this.inventoryItemIdentifier = new InventoryItemIdentifier();
        this.stockLevel = stockLevel;
    }

}


