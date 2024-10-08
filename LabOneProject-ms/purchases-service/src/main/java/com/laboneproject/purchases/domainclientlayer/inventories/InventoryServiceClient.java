package com.laboneproject.purchases.domainclientlayer.inventories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboneproject.purchases.domainclientlayer.sneakers.SneakerModel;
import com.laboneproject.purchases.utils.HttpErrorInfo;

import com.laboneproject.purchases.utils.exceptions.InvalidInputException;
import com.laboneproject.purchases.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@Slf4j
public class InventoryServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String INVENTORIES_SERVICE_BASE_URL;

    private InventoryServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                                 @Value("${app.inventory-service.host}") String inventoryServiceHost,
                                 @Value("${app.inventory-service.port}") String inventoryServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = objectMapper;

        INVENTORIES_SERVICE_BASE_URL = "http://" + inventoryServiceHost + ":" + inventoryServicePort + "/api/v1/inventories";
    }

    public InventoryModel getInventoryByInventoryId(String inventoryId){
        try{

            String url = INVENTORIES_SERVICE_BASE_URL + "/" + inventoryId;

            InventoryModel inventoryModel = restTemplate.getForObject(url, InventoryModel.class);

            return inventoryModel;
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
