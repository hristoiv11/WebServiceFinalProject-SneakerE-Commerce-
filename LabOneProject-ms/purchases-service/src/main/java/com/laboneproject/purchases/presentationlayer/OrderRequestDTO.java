package com.laboneproject.purchases.presentationlayer;



import com.laboneproject.purchases.dataaccesslayer.OrderStatus;
import com.laboneproject.purchases.dataaccesslayer.ShippingAddress;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderRequestDTO {


    private String inventoryId;
    private String sneakerId;
    private Integer quantityBought;
    private OrderStatus orderStatus;
    private ShippingAddress shippingAddress;

}


