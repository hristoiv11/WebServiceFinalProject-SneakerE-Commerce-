package com.laboneproject.customers.datamapperlayer;


import com.laboneproject.customers.dataaccesslayer.Customer;
import com.laboneproject.customers.dataaccesslayer.CustomerIdentifier;
import com.laboneproject.customers.dataaccesslayer.Wishlist;
import com.laboneproject.customers.presentationlayer.CustomerRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

    @Mapping(target = "id", ignore= true)
    Customer requestDTOToEntity(CustomerRequestDTO customerRequestDTO,
                                CustomerIdentifier customerIdentifier , Wishlist wishlist);
}

