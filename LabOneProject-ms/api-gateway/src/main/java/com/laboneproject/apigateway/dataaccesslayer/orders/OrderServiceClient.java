package com.laboneproject.apigateway.dataaccesslayer.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.orders.OrderRequestDTO;
import com.laboneproject.apigateway.presentationlayer.orders.OrderResponseDTO;
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
public class OrderServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String ORDERS_SERVICE_BASE_URL;

    private OrderServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                                   @Value("${app.purchases-service.host}") String ordersServiceHost,
                                   @Value("${app.purchases-service.port}") String ordersServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = objectMapper;

        ORDERS_SERVICE_BASE_URL = "http://" + ordersServiceHost + ":" + ordersServicePort + "/api/v1/customers/{customerId}/orders";
    }

    public List<OrderResponseDTO> getAllOrders(String customerId){
        try{

            //String url = ORDERS_SERVICE_BASE_URL;
            String url = ORDERS_SERVICE_BASE_URL.replace("{customerId}", customerId);
            OrderResponseDTO[] orderResponseDTOS = restTemplate.getForObject(url, OrderResponseDTO[].class);

            return Arrays.asList(orderResponseDTOS);
        } catch (HttpClientErrorException ex){

            throw handleHttpClientException(ex);

        }
    }


    public OrderResponseDTO getOrderByOrderId(String orderId,String customerId){
        try{

            //String url = ORDERS_SERVICE_BASE_URL + "/" + orderId;
            String url= ORDERS_SERVICE_BASE_URL.replace("{customerId}", customerId)  + "/" + orderId;

            OrderResponseDTO orderResponseDTO = restTemplate.getForObject(url, OrderResponseDTO.class);

            return orderResponseDTO;
        } catch (HttpClientErrorException ex){

            throw handleHttpClientException(ex);

        }
    }

    public OrderResponseDTO addOrder(OrderRequestDTO orderRequestDTO,String customerId){
        try {
            //String url = ORDERS_SERVICE_BASE_URL;
            String url = ORDERS_SERVICE_BASE_URL.replace("{customerId}", customerId);

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<OrderRequestDTO> requestEntity = new HttpEntity<>(orderRequestDTO, headers);

            // Send the POST request to add the league
            OrderResponseDTO orderResponseDTO = restTemplate.postForObject(url, requestEntity, OrderResponseDTO.class);

            return orderResponseDTO;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public OrderResponseDTO updateOrder(String orderId, OrderRequestDTO orderRequestDTO,String customerId) {
        try {
            //String url = ORDERS_SERVICE_BASE_URL + "/" + orderId;
            String url = ORDERS_SERVICE_BASE_URL.replace("{customerId}", customerId) +  "/" +  orderId;

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the league data in the body and headers
            HttpEntity<OrderRequestDTO> requestEntity = new HttpEntity<>(orderRequestDTO, headers);

            // Send the PUT request to update the league
            restTemplate.put(url, requestEntity);

            // Assuming the leagues service returns the updated league data, you can fetch it
            OrderResponseDTO updatedOrder = restTemplate.getForObject(url, OrderResponseDTO.class);

            return updatedOrder;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteOrder(String orderId,String customerId) {
        try {
            //String url = ORDERS_SERVICE_BASE_URL + "/" + orderId;
            String url =ORDERS_SERVICE_BASE_URL.replace("{customerId}", customerId) + "/" + orderId;
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

