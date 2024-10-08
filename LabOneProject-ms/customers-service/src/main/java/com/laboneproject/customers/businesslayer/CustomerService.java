package com.laboneproject.customers.businesslayer;



import com.laboneproject.customers.presentationlayer.CustomerRequestDTO;
import com.laboneproject.customers.presentationlayer.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerResponseDTO> getAllCustomers();
    CustomerResponseDTO getCustomerById(String customerId);
    CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO updateCustomer(CustomerRequestDTO customerRequestDTO, String customerId);
    void deleteCustomer(String customerId);


}
