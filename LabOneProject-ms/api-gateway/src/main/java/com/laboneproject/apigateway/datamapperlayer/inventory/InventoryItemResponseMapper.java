package com.laboneproject.apigateway.datamapperlayer.inventory;

import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemController;
import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


import java.util.List;



@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface InventoryItemResponseMapper {

    InventoryItemResponseDTO responseModelToResponseModel(InventoryItemResponseDTO inventoryItemResponseDTO);

    List<InventoryItemResponseDTO> responseModelListToReposeList(List<InventoryItemResponseDTO> inventoryItemResponseDTOList);

    /*
    @AfterMapping
    default void addLinks(@MappingTarget InventoryItemResponseDTO inventoryItemResponseDTO){

        //do a single link

        Link selfLink = linkTo(methodOn(InventoryItemController.class)
                .getInventoryItemById(inventoryItemResponseDTO.getInventoryId()))
                .withSelfRel();

        inventoryItemResponseDTO.add(selfLink);


        Link allLink = linkTo(methodOn(InventoryItemController.class)
                .getAllInventory())
                .withRel("all inventories");

        inventoryItemResponseDTO.add(allLink);

    }

     */

}

