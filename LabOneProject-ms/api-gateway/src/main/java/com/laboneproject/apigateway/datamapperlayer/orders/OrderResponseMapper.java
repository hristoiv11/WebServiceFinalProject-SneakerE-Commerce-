package com.laboneproject.apigateway.datamapperlayer.orders;

import com.laboneproject.apigateway.presentationlayer.customers.CustomerController;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.orders.OrderController;
import com.laboneproject.apigateway.presentationlayer.orders.OrderResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


import java.util.List;



@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface OrderResponseMapper {

    OrderResponseDTO responseModelToResponseModel(OrderResponseDTO orderResponseDTO);

    List<OrderResponseDTO> responseModelListToReposeList(List<OrderResponseDTO> orderResponseDTOList);

    /*
    @AfterMapping
    default void addLinks(@MappingTarget OrderResponseDTO orderResponseDTO){

        //do a single link

        Link selfLink = linkTo(methodOn(OrderController.class)
                .getOrderByOrderId(orderResponseDTO.getOrderId()))
                .withSelfRel();

        orderResponseDTO.add(selfLink);


        Link allLink = linkTo(methodOn(OrderController.class)
                .getAllOrders())
                .withRel("all orders");

        orderResponseDTO.add(allLink);

    }

     */

}