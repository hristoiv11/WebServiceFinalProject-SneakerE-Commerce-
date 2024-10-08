package com.laboneproject.purchases.mapperlayer;



import com.laboneproject.purchases.dataaccesslayer.Order;
import com.laboneproject.purchases.presentationlayer.OrderResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderResponseMapper {

    @Mapping(expression = "java(order.getOrderIdentifier().getOrderId())", target = "orderId")
    @Mapping(expression = "java(order.getQuantityBought())",target = "quantityBought")
    @Mapping(expression = "java(order.getInventoryModel().getInventoryId())", target = "inventoryId")
    //@Mapping(expression = "java(order.getInventoryModel().getAvailableLevel())",target = "availableLevel")
    //@Mapping(expression = "java(order.getInventoryModel().getStockLevel().getAvailableLevel())",target = "availableLevel")
    //@Mapping(expression = "java(order.getInventoryModel().getStockLevel().getRestockLevel())",target = "restockLevel")
    //@Mapping(expression = "java(order.getInventoryModel().getRestockLevel())",target = "restockLevel")
    @Mapping(expression = "java(order.getCustomerModel().getCustomerId())",target = "customerId")
    @Mapping(expression = "java(order.getCustomerModel().getCustomerFName())",target = "customerFName")
    @Mapping(expression = "java(order.getCustomerModel().getCustomerLName())",target = "customerLName")
    @Mapping(expression = "java(order.getCustomerModel().getNumber())",target = "number")
    @Mapping(expression = "java(order.getCustomerModel().getEmail())",target = "email")
    //@Mapping(expression = "java(order.getCustomerModel().getCustomerPreferredSneaker())",target = "customerPreferredSneaker")
    //@Mapping(expression = "java(order.getCustomerModel().getCustomerPreferredBrand())",target = "customerPreferredBrand")
    //@Mapping(expression = "java(order.getCustomerModel().getCustomerPreferredSize())",target = "customerPreferredSize")
    @Mapping(expression = "java(order.getSneakerModel().getSneakerId())",target = "sneakerId")
    @Mapping(expression = "java(order.getSneakerModel().getModel())",target = "model")
    @Mapping(expression = "java(order.getSneakerModel().getPrice())",target = "price")
    @Mapping(expression = "java(order.getSneakerModel().getSize())",target = "size")
    @Mapping(expression = "java(order.getSneakerModel().getColor())",target = "color")
    //@Mapping(expression = "java(order.getSneakerModel().getReleaseYear())",target = "releaseYear")
    //@Mapping(expression = "java(order.getSneakerModel().getAvailableStore())",target = "availableStore")
    //@Mapping(expression = "java(order.getSneakerModel().getDescription())",target = "description")
    //@Mapping(expression = "java(order.getSneakerModel().getType())",target = "type")


    OrderResponseDTO entityToResponseDTO(Order order);

    List<OrderResponseDTO> entityListToResponseModelList(List<Order> orders);

    //List<OrderResponseDTO> entityListToResponseDTOList(List<Order> orderList,InventoryItem inventoryItem, Customer customer, Sneaker sneaker);


    /*
    @AfterMapping
    default void addLinks(@MappingTarget OrderResponseDTO orderResponseDTO, Order order) {
        //self link
        Link selfLink = linkTo(methodOn(OrderController.class).getOrderById(orderResponseDTO.getOrderId(),orderResponseDTO.getCustomerId())).withSelfRel();
        orderResponseDTO.add(selfLink);

        //all inventories link
        Link ordersLink = linkTo(methodOn(OrderController.class).getAllOrders(orderResponseDTO.getCustomerId())).withRel("orders");
        orderResponseDTO.add(ordersLink);



    }

     */

    }




