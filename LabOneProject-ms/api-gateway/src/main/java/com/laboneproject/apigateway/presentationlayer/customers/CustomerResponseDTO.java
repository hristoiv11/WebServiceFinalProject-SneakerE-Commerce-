package com.laboneproject.apigateway.presentationlayer.customers;



import com.laboneproject.apigateway.dataaccesslayer.customers.CustomerStatus;
import lombok.*;
;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@EqualsAndHashCode(callSuper=true)
public class CustomerResponseDTO {

    private String customerId;
    private String customerFName;
    private String customerLName;
    private String number;
    private String email;
    private String customerPreferredSneaker;
    private String customerPreferredBrand;
    private String customerPreferredSize;
    private CustomerStatus customerStatus;
}

//extends RepresentationModel<CustomerResponseDTO>