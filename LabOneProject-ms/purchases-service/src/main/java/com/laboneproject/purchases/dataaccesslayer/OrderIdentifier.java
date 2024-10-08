package com.laboneproject.purchases.dataaccesslayer;


import lombok.Getter;

import java.util.UUID;



@Getter
public class OrderIdentifier {

    private String orderId;
    public OrderIdentifier(){
        this.orderId= UUID.randomUUID().toString();
    }

    /*
    public OrderIdentifier(String orderId) {
        this.orderId = orderId;
    }

     */
}

