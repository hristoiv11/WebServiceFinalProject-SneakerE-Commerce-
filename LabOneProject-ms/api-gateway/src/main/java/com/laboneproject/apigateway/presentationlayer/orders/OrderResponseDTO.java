package com.laboneproject.apigateway.presentationlayer.orders;

import com.laboneproject.apigateway.dataaccesslayer.orders.OrderStatus;
import com.laboneproject.apigateway.dataaccesslayer.orders.ShippingAddress;
import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemResponseDTO;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@EqualsAndHashCode(callSuper=true)
public class OrderResponseDTO {

    private String orderId;
    private String inventoryId;
    private String customerFName;
    private String customerLName;
    private String number;
    private String email;
    private String sneakerId;
    private String model;
    private String price;
    private Integer size;
    private String color;
    private Integer quantityBought;
    private OrderStatus orderStatus;
    private ShippingAddress shippingAddress;
}
//extends RepresentationModel<OrderResponseDTO>