package com.laboneproject.inventory.datamapperlayer;



import com.laboneproject.inventory.dataaccesslayer.InventoryItem;
import com.laboneproject.inventory.presentationlayer.InventoryItemController;
import com.laboneproject.inventory.presentationlayer.InventoryItemResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Mapper(componentModel = "spring")
public interface InventoryItemResponseMapper {

    @Mapping(expression = "java(inventoryItem.getInventoryItemIdentifier().getInventoryId())", target = "inventoryId")
    @Mapping(expression = "java(inventoryItem.getStockLevel().getAvailableLevel())", target = "availableLevel")
    @Mapping(expression = "java(inventoryItem.getStockLevel().getRestockLevel())", target = "restockLevel")
    InventoryItemResponseDTO entityToResponseDTO(InventoryItem inventoryItem);

    List<InventoryItemResponseDTO> entityListToResponseDTOList(List<InventoryItem> inventoryList);


    @AfterMapping
    default void addLinks(@MappingTarget InventoryItemResponseDTO inventoryResponseDTO, InventoryItem inventoryItem) {
        //self link
        Link selfLink = linkTo(methodOn(InventoryItemController.class).getInventoryItemById(inventoryResponseDTO.getInventoryId())).withSelfRel();
        inventoryResponseDTO.add(selfLink);


        //all inventories link
        Link inventoriesLink = linkTo(methodOn(InventoryItemController.class).getAllInventory()).withRel("inventories");
        inventoryResponseDTO.add(inventoriesLink);

    }


}

