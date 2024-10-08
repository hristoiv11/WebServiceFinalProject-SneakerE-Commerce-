package com.laboneproject.apigateway.businesslayer.customers;

import com.laboneproject.apigateway.dataaccesslayer.customers.CustomersServiceClient;
import com.laboneproject.apigateway.datamapperlayer.customers.CustomerResponseMapper;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomersServiceClient customersServiceClient;
    private final CustomerResponseMapper customerResponseMapper;

    public CustomerServiceImpl(CustomersServiceClient customersServiceClient, CustomerResponseMapper customerResponseMapper) {
        this.customersServiceClient = customersServiceClient;
        this.customerResponseMapper = customerResponseMapper;
    }
    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerResponseMapper.responseModelListToReposeList(customersServiceClient.getAllCustomers());
    }

    @Override
    public CustomerResponseDTO getCustomerByCustomerId(String customerId) {
        return customerResponseMapper.responseModelToResponseModel(customersServiceClient.getCustomerByCustomerId(customerId));
    }

    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO) {
        return customerResponseMapper.responseModelToResponseModel(customersServiceClient.addCustomer(customerRequestDTO));
    }

    @Override
    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customerRequestDTO, String customerId) {
        return customerResponseMapper.responseModelToResponseModel(customersServiceClient.updateCustomer(customerId,customerRequestDTO));
    }

    @Override
    public void deleteCustomer(String customerId) {
       customersServiceClient.deleteCustomer(customerId);
    }


}
