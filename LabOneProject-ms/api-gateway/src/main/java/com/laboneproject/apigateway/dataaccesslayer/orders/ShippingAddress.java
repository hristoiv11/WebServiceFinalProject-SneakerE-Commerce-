package com.laboneproject.apigateway.dataaccesslayer.orders;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ShippingAddress {

    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;

}

