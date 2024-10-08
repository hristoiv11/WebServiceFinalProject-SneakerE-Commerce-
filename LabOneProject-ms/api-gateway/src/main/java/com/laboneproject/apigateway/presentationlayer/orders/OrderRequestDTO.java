package com.laboneproject.apigateway.presentationlayer.orders;

import com.laboneproject.apigateway.dataaccesslayer.orders.OrderStatus;
import com.laboneproject.apigateway.dataaccesslayer.orders.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderRequestDTO {

    private String inventoryId;
    private String sneakerId;
    private Integer quantityBought;
    private ShippingAddress shippingAddress;
    private OrderStatus orderStatus;

}
