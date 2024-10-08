package com.laboneproject.purchases.mapperlayer;


import com.laboneproject.purchases.dataaccesslayer.Order;
import com.laboneproject.purchases.dataaccesslayer.OrderIdentifier;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerModel;
import com.laboneproject.purchases.domainclientlayer.inventories.InventoryModel;
import com.laboneproject.purchases.domainclientlayer.sneakers.SneakerModel;
import com.laboneproject.purchases.presentationlayer.OrderRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderRequestMapper {

    @Mapping(target = "id", ignore= true)
    Order requestDTOToEntity(OrderRequestDTO orderRequestDTO,
                             OrderIdentifier orderIdentifier,
                             InventoryModel inventoryModel,
                             CustomerModel customerModel,
                             SneakerModel sneakerModel);
}

