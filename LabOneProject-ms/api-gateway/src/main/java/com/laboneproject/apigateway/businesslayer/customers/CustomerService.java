package com.laboneproject.apigateway.businesslayer.customers;

import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerResponseDTO> getAllCustomers();
    CustomerResponseDTO getCustomerByCustomerId(String customerId);
    CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO updateCustomer(CustomerRequestDTO customerRequestDTO, String customerId);
    void deleteCustomer(String customerId);
}
