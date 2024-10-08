package com.laboneproject.customers.presentationlayer;


import com.laboneproject.customers.dataaccesslayer.CustomerStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class CustomerResponseDTO extends RepresentationModel<CustomerResponseDTO>{

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


