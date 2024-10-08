package com.laboneproject.customers.datamapperlayer;


import com.laboneproject.customers.dataaccesslayer.Customer;
import com.laboneproject.customers.presentationlayer.CustomerController;
import com.laboneproject.customers.presentationlayer.CustomerResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {

    @Mapping(expression = "java(customer.getCustomerIdentifier().getCustomerId())", target = "customerId")

    @Mapping(expression = "java(customer.getWishlist().getCustomerPreferredSneaker())", target = "customerPreferredSneaker")
    @Mapping(expression = "java(customer.getWishlist().getCustomerPreferredBrand())", target = "customerPreferredBrand")
    @Mapping(expression = "java(customer.getWishlist().getCustomerPreferredSize())", target = "customerPreferredSize")
    CustomerResponseDTO entityToResponseDTO(Customer customer);
    List<CustomerResponseDTO> entityListToResponseDTOList(List<Customer> customerList);


    @AfterMapping
    default void addLinks(@MappingTarget CustomerResponseDTO customerResponseDTO, Customer customer) {
        //self link
        Link selfLink = linkTo(methodOn(CustomerController.class).getCustomerById(customerResponseDTO.getCustomerId())).withSelfRel();
        customerResponseDTO.add(selfLink);


        //all inventories link
        Link customersLink = linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers");
        customerResponseDTO.add(customersLink);
    }




}