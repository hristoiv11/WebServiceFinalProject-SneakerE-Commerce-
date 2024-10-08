package com.laboneproject.purchases.domainclientlayer.customers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class CustomerModel {
    String customerId;
    String customerFName;
    String customerLName;
    String number;
    String email;
    //Wishlist wishlist;
    String customerPreferredSneaker;
    String customerPreferredBrand;
    String customerPreferredSize;
    CustomerStatus customerStatus;
}
