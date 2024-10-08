package com.laboneproject.apigateway.dataaccesslayer.sneakers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboneproject.apigateway.presentationlayer.orders.OrderRequestDTO;
import com.laboneproject.apigateway.presentationlayer.orders.OrderResponseDTO;
import com.laboneproject.apigateway.presentationlayer.sneakers.SneakerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.sneakers.SneakerResponseDTO;
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
public class SneakerServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String SNEAKERS_SERVICE_BASE_URL;

    private SneakerServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                               @Value("${app.sneakers-service.host}") String sneakersServiceHost,
                               @Value("${app.sneakers-service.port}") String sneakersServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = objectMapper;

        SNEAKERS_SERVICE_BASE_URL = "http://" + sneakersServiceHost + ":" + sneakersServicePort + "/api/v1/sneakers";
    }

    public List<SneakerResponseDTO> getAllSneakers(){
        try{

            String url = SNEAKERS_SERVICE_BASE_URL;

            SneakerResponseDTO[] sneakerResponseDTOS = restTemplate.getForObject(url, SneakerResponseDTO[].class);

            return Arrays.asList(sneakerResponseDTOS);
        } catch (HttpClientErrorException ex){

            throw handleHttpClientException(ex);

        }
    }


    public SneakerResponseDTO getSneakerBySneakerId(String sneakerId){
        try{

            String url = SNEAKERS_SERVICE_BASE_URL + "/" + sneakerId;

            SneakerResponseDTO sneakerResponseDTO = restTemplate.getForObject(url, SneakerResponseDTO.class);

            return sneakerResponseDTO;
        } catch (HttpClientErrorException ex){

            throw handleHttpClientException(ex);

        }
    }

    public SneakerResponseDTO addSneaker(SneakerRequestDTO sneakerRequestDTO){
        try {
            String url = SNEAKERS_SERVICE_BASE_URL;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<SneakerRequestDTO> requestEntity = new HttpEntity<>(sneakerRequestDTO, headers);

            // Send the POST request to add the league
            SneakerResponseDTO sneakerResponseDTO = restTemplate.postForObject(url, requestEntity, SneakerResponseDTO.class);

            return sneakerResponseDTO;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public SneakerResponseDTO updateSneaker(String sneakerId, SneakerRequestDTO sneakerRequestDTO) {
        try {
            String url = SNEAKERS_SERVICE_BASE_URL + "/" + sneakerId;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<SneakerRequestDTO> requestEntity = new HttpEntity<>(sneakerRequestDTO, headers);

            // Send the PUT request to update the league
            restTemplate.put(url, requestEntity);

            // Assuming the leagues service returns the updated league data, you can fetch it
            SneakerResponseDTO updatedSneaker = restTemplate.getForObject(url, SneakerResponseDTO.class);

            return updatedSneaker;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteSneaker(String sneakerId) {
        try {
            String url = SNEAKERS_SERVICE_BASE_URL + "/" + sneakerId;
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
