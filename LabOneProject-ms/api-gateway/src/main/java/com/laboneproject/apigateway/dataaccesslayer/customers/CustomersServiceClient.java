package com.laboneproject.apigateway.dataaccesslayer.customers;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.utils.HttpErrorInfo;
import com.laboneproject.apigateway.utils.exceptions.InvalidInputException;
import com.laboneproject.apigateway.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@Slf4j
public class CustomersServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String CUSTOMERS_SERVICE_BASE_URL;

    private CustomersServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                                   @Value("${app.customers-service.host}") String customersServiceHost,
                                   @Value("${app.customers-service.port}") String customerServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = objectMapper;

        CUSTOMERS_SERVICE_BASE_URL = "http://" + customersServiceHost + ":" + customerServicePort + "/api/v1/customers";
    }

    public List<CustomerResponseDTO> getAllCustomers(){
        try{

            String url = CUSTOMERS_SERVICE_BASE_URL;

           CustomerResponseDTO[] customerResponseDTOS = restTemplate.getForObject(url, CustomerResponseDTO[].class);

            return Arrays.asList(customerResponseDTOS);
        } catch (HttpClientErrorException ex){

            throw handleHttpClientException(ex);

        }
    }


    public CustomerResponseDTO getCustomerByCustomerId(String customerId){
        try{

            String url = CUSTOMERS_SERVICE_BASE_URL + "/" + customerId;

            CustomerResponseDTO customerResponseDTO = restTemplate.getForObject(url, CustomerResponseDTO.class);

            return customerResponseDTO;
        } catch (HttpClientErrorException ex){

            throw handleHttpClientException(ex);

        }
    }

    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO){
        try {
            String url = CUSTOMERS_SERVICE_BASE_URL;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<CustomerRequestDTO> requestEntity = new HttpEntity<>(customerRequestDTO, headers);

            // Send the POST request to add the league
            CustomerResponseDTO customerResponseDTO = restTemplate.postForObject(url, requestEntity, CustomerResponseDTO.class);

            return customerResponseDTO;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public CustomerResponseDTO updateCustomer(String customerId, CustomerRequestDTO customerRequestDTO) {
        try {
            String url = CUSTOMERS_SERVICE_BASE_URL + "/" + customerId;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<CustomerRequestDTO> requestEntity = new HttpEntity<>(customerRequestDTO, headers);

            // Send the PUT request to update the league
            restTemplate.put(url, requestEntity);

            // Assuming the leagues service returns the updated league data, you can fetch it
            CustomerResponseDTO updatedCustomer = restTemplate.getForObject(url, CustomerResponseDTO.class);

            return updatedCustomer;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteCustomer(String customerId) {
        try {
            String url = CUSTOMERS_SERVICE_BASE_URL + "/" + customerId;
            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
    //include all possible responses from the client
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
}

