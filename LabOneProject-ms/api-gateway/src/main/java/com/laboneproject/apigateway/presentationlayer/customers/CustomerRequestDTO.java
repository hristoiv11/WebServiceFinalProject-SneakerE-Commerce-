package com.laboneproject.apigateway.presentationlayer.customers;


import com.laboneproject.apigateway.dataaccesslayer.customers.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerRequestDTO {

    private String customerFName;
    private String customerLName;
    private String number;
    private String email;
    private String customerPreferredSneaker;
    private String customerPreferredBrand;
    private String customerPreferredSize;
    private CustomerStatus customerStatus;
}
