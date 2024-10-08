package com.laboneproject.inventory.presentationlayer;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class InventoryItemResponseDTO extends RepresentationModel<InventoryItemResponseDTO>{

    private String inventoryId;
    private String availableLevel;
    private String restockLevel;
}


