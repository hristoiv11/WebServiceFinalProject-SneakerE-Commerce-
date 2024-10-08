package com.laboneproject.inventory.presentationlayer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InventoryItemRequestDTO {

    private String availableLevel;
    private String restockLevel;

}


