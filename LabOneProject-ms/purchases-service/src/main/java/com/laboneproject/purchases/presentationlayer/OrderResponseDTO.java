package com.laboneproject.purchases.presentationlayer;



import com.laboneproject.purchases.dataaccesslayer.OrderStatus;
import com.laboneproject.purchases.dataaccesslayer.ShippingAddress;

import lombok.*;


import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {

    private String orderId;
    private String inventoryId;
    //StockLevel stockLevel;
    //private String availableLevel;
    //private String restockLevel;
    private String customerId;
    private String customerFName;
    private String customerLName;
    private String number;
    private String email;
    //private String customerPreferredSneaker;
    //private String customerPreferredBrand;
    //private String customerPreferredSize;
    private String sneakerId;
    private String model;
    private String price;
    private Integer size;
    private String color;
    //private String releaseYear;
    //private String availableStore;
    //private String description;
    //private String type;
    private Integer quantityBought;
    private OrderStatus orderStatus;
    //private List<ShippingAddress> shippingAddress;
    private ShippingAddress shippingAddress;
}


//extends RepresentationModel<OrderResponseDTO>
