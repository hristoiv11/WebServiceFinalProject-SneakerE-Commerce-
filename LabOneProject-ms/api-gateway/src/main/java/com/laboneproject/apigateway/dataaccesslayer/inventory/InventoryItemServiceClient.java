package com.laboneproject.apigateway.dataaccesslayer.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemRequestDTO;
import com.laboneproject.apigateway.presentationlayer.inventory.InventoryItemResponseDTO;
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
public class InventoryItemServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String INVENTORIES_SERVICE_BASE_URL;

    private InventoryItemServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                                       @Value("${app.inventory-service.host}") String inventoryServiceHost,
                                       @Value("${app.inventory-service.port}") String inventoryServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = objectMapper;

        INVENTORIES_SERVICE_BASE_URL = "http://" + inventoryServiceHost + ":" + inventoryServicePort + "/api/v1/inventories";
    }

    public List<InventoryItemResponseDTO> getAllInventories(){
        try{

            String url = INVENTORIES_SERVICE_BASE_URL;

            InventoryItemResponseDTO[] inventoryItemResponseDTOS = restTemplate.getForObject(url, InventoryItemResponseDTO[].class);

            return Arrays.asList(inventoryItemResponseDTOS);
        } catch (HttpClientErrorException ex){

            throw handleHttpClientException(ex);

        }
    }


    public InventoryItemResponseDTO getInventoryByInventoryId(String inventoryId){
        try{

            String url = INVENTORIES_SERVICE_BASE_URL + "/" + inventoryId;

            InventoryItemResponseDTO inventoryItemResponseDTO = restTemplate.getForObject(url, InventoryItemResponseDTO.class);

            return inventoryItemResponseDTO;
        } catch (HttpClientErrorException ex){

            throw handleHttpClientException(ex);

        }
    }

    public InventoryItemResponseDTO addInventoryItem(InventoryItemRequestDTO inventoryItemRequestDTO){
        try {
            String url = INVENTORIES_SERVICE_BASE_URL;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<InventoryItemRequestDTO> requestEntity = new HttpEntity<>(inventoryItemRequestDTO, headers);

            // Send the POST request to add the league
            InventoryItemResponseDTO inventoryItemResponseDTO = restTemplate.postForObject(url, requestEntity, InventoryItemResponseDTO.class);

            return inventoryItemResponseDTO;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public InventoryItemResponseDTO updateInventoryItem(String inventoryId, InventoryItemRequestDTO inventoryItemRequestDTO) {
        try {
            String url = INVENTORIES_SERVICE_BASE_URL + "/" + inventoryId;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<InventoryItemRequestDTO> requestEntity = new HttpEntity<>(inventoryItemRequestDTO, headers);

            // Send the PUT request to update the league
            restTemplate.put(url, requestEntity);

            // Assuming the leagues service returns the updated league data, you can fetch it
            InventoryItemResponseDTO inventoryItemResponseDTO = restTemplate.getForObject(url, InventoryItemResponseDTO.class);

            return inventoryItemResponseDTO;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteInventoryItem(String inventoryId) {
        try {
            String url = INVENTORIES_SERVICE_BASE_URL + "/" + inventoryId;
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


