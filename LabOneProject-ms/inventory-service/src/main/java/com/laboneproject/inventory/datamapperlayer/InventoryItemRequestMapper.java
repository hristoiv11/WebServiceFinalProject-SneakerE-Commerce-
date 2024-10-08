package com.laboneproject.inventory.datamapperlayer;



import com.laboneproject.inventory.dataaccesslayer.InventoryItem;
import com.laboneproject.inventory.dataaccesslayer.InventoryItemIdentifier;
import com.laboneproject.inventory.dataaccesslayer.StockLevel;
import com.laboneproject.inventory.presentationlayer.InventoryItemRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface InventoryItemRequestMapper {
    @Mapping(target = "id", ignore= true)
    InventoryItem requestDTOToEntity(InventoryItemRequestDTO inventoryRequestDTO,
                                     InventoryItemIdentifier inventoryItemIdentifier, StockLevel stockLevel);
}


