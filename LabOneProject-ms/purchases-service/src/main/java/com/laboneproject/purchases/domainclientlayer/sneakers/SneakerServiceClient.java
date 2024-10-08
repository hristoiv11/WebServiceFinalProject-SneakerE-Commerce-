package com.laboneproject.purchases.domainclientlayer.sneakers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerModel;
import com.laboneproject.purchases.utils.HttpErrorInfo;

import com.laboneproject.purchases.utils.exceptions.InvalidInputException;
import com.laboneproject.purchases.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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

    public SneakerModel getSneakerBySneakerId(String sneakerId){
        try{

            String url = SNEAKERS_SERVICE_BASE_URL + "/" + sneakerId;

            SneakerModel sneakerModel = restTemplate.getForObject(url, SneakerModel.class);

            return sneakerModel;
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
