package com.laboneproject.apigateway.datamapperlayer.customers;


import com.laboneproject.apigateway.presentationlayer.customers.CustomerController;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;


import java.util.List;


@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface CustomerResponseMapper {

    CustomerResponseDTO responseModelToResponseModel(CustomerResponseDTO customerResponseDTO);

    List<CustomerResponseDTO> responseModelListToReposeList(List<CustomerResponseDTO> customerResponseDTOList);

    /*
    @AfterMapping
    default void addLinks(@MappingTarget CustomerResponseDTO customerResponseDTO){

        //do a single link

        Link selfLink = linkTo(methodOn(CustomerController.class)
                .getCustomerById(customerResponseDTO.getCustomerId()))
                .withSelfRel();

        customerResponseDTO.add(selfLink);


        Link allLink = linkTo(methodOn(CustomerController.class)
                .getAllCustomers())
                .withRel("all customers");

        customerResponseDTO.add(allLink);

    }

     */

}
