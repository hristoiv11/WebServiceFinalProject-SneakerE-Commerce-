package com.laboneproject.customers.presentationlayer;

import com.laboneproject.customers.businesslayer.CustomerService;
import com.laboneproject.customers.dataaccesslayer.CustomerStatus;
import com.laboneproject.customers.utils.exceptions.InUseException;
import com.laboneproject.customers.utils.exceptions.InvalidInputException;
import com.laboneproject.customers.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CustomerController.class)
@AutoConfigureMockMvc
class CustomerControllerUnitTest {

    private final String FOUND_CUSTOMER_ID = "009";

    //private final String NOT_FOUND_CUSTOMER_ID = "042";

    private final String INVALID_CUSTOMER_ID = "000";

    //private final String URI_CUSTOMERS = "/api/v1/customers";

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    CustomerController customerController;

    @MockBean
    private CustomerService customerService;


    @Test
    public void whenCustomerDoNotExists_ReturnEmptyList(){

        //arrange

        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        //act
        ResponseEntity<List<CustomerResponseDTO>> responseEntity = customerController.getAllCustomers();

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void whenCustomerExists_thenReturnCustomer(){
        //arrange

        CustomerRequestDTO customerRequestDTO= CustomerRequestDTO.builder().build();
        CustomerResponseDTO customerResponseDTO= CustomerResponseDTO.builder().build();

        when(customerService.addCustomer(customerRequestDTO)).thenReturn(customerResponseDTO);

        //act
        ResponseEntity<CustomerResponseDTO> responseEntity= customerController.addCustomer(customerRequestDTO);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(customerResponseDTO, responseEntity.getBody());
        verify(customerService, times(1)).addCustomer(customerRequestDTO);
    }

    @Test
    public void whenCustomerExist_thenReturnUpdatedCustomer() throws NotFoundException {

        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder().customerId(FOUND_CUSTOMER_ID).build();

        when(customerService.updateCustomer(customerRequestDTO, FOUND_CUSTOMER_ID)).thenReturn(customerResponseDTO);

        // Act
        ResponseEntity<CustomerResponseDTO> responseEntity = customerController.updateCustomer(customerRequestDTO, FOUND_CUSTOMER_ID);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(FOUND_CUSTOMER_ID, responseEntity.getBody().getCustomerId());
        verify(customerService, times(1)).updateCustomer(customerRequestDTO, FOUND_CUSTOMER_ID);

    }

    @Test
    public void whenDeleteCustomer_thenStatusNoContent() throws Exception {
        doNothing().when(customerService).deleteCustomer(FOUND_CUSTOMER_ID);

        mockMvc.perform(delete("/api/v1/customers/{customerId}", FOUND_CUSTOMER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenGetNonExistentCustomer_thenThrowNotFoundException() throws Exception {
        String nonExistentId = INVALID_CUSTOMER_ID;
        when(customerService.getCustomerById(nonExistentId))
                .thenThrow(new NotFoundException("Customer not found"));

        mockMvc.perform(get("/customers/{id}", nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenAddCustomerWithInvalidInput_thenThrowInvalidInputException() {
        CustomerRequestDTO invalidCustomerDTO = new CustomerRequestDTO("butush","1000","111-111-111","but@gmail.com","Nike vomero 5","Nike","10", CustomerStatus.BANNED);
        doThrow(new InvalidInputException("Invalid input")).when(customerService).addCustomer(invalidCustomerDTO);

        assertThrows(InvalidInputException.class, () -> customerController.addCustomer(invalidCustomerDTO));
    }

    @Test
    public void whenDeleteCustomerInUse_thenThrowInUseException() {
        String inUseCustomerId = "010";
        doThrow(new InUseException("Customer is currently in use")).when(customerService).deleteCustomer(inUseCustomerId);

        assertThrows(InUseException.class, () -> customerController.deleteCustomer(inUseCustomerId));
    }


}