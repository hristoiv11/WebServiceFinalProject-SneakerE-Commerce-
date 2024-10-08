package com.laboneproject.apigateway.presentationlayer.customers;


import com.laboneproject.apigateway.businesslayer.customers.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService)   {
        this.customerService = customerService;
    }

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    @GetMapping(

            value ="/{customerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable String customerId) {
        CustomerResponseDTO customerResponseDTO = customerService.getCustomerByCustomerId(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }


    @PostMapping(
              value = "",
              produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDTO> addCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO addedCustomer = customerService.addCustomer(customerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCustomer);
    }

    @PutMapping(

           value = "/{customerId}",
           produces = MediaType.APPLICATION_JSON_VALUE
               )
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @RequestBody CustomerRequestDTO customerRequestDTO,
            @PathVariable String customerId) {
        CustomerResponseDTO updatedCustomer = customerService.updateCustomer(customerRequestDTO, customerId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

    @DeleteMapping(

            value = "/{customerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
