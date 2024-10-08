package com.laboneproject.apigateway.businesslayer.customers;

import com.laboneproject.apigateway.dataaccesslayer.customers.CustomersServiceClient;
import com.laboneproject.apigateway.datamapperlayer.customers.CustomerResponseMapper;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceUnitTest {

    @Mock
    private CustomersServiceClient customersServiceClient;

    @Mock
    private CustomerResponseMapper customerResponseMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testGetAllCustomers() {
        when(customersServiceClient.getAllCustomers()).thenReturn(Arrays.asList(new CustomerResponseDTO()));
        when(customerResponseMapper.responseModelListToReposeList(any())).thenReturn(Arrays.asList(new CustomerResponseDTO()));

        List<CustomerResponseDTO> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(customersServiceClient).getAllCustomers();
        verify(customerResponseMapper).responseModelListToReposeList(any());
    }
}
