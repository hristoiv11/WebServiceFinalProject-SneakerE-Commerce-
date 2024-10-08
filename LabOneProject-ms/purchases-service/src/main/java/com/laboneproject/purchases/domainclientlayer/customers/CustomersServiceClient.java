package com.laboneproject.purchases.domainclientlayer.customers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboneproject.purchases.utils.HttpErrorInfo;
import com.laboneproject.purchases.utils.exceptions.InvalidInputException;
import com.laboneproject.purchases.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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
                                   @Value("${app.customers-service.port}") String customersServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = objectMapper;

        CUSTOMERS_SERVICE_BASE_URL = "http://" + customersServiceHost + ":" + customersServicePort + "/api/v1/customers";
    }

    public CustomerModel getCustomerByCustomerId(String customerId){
        try{

            String url = CUSTOMERS_SERVICE_BASE_URL + "/" + customerId;

            CustomerModel customerModel = restTemplate.getForObject(url, CustomerModel.class);

            return customerModel;
        } catch (HttpClientErrorException ex){

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
