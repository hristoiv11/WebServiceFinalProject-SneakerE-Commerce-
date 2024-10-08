package com.laboneproject.apigateway.presentationlayer.inventory;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class InventoryItemResponseDTO  {
    private String inventoryId;
    private String availableLevel;
    private String restockLevel;
}


//extends RepresentationModel<InventoryItemResponseDTO>