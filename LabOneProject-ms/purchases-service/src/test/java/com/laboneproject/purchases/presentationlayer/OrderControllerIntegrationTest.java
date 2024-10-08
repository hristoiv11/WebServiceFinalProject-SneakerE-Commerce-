package com.laboneproject.purchases.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboneproject.purchases.dataaccesslayer.*;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerModel;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerStatus;
import com.laboneproject.purchases.utils.exceptions.NotFoundException;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
//@WebFluxTest(OrderController.class)
class OrderControllerIntegrationTest {



    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestTemplate restTemplate;
    private MockRestServiceServer mockRestServiceServer;
    private ObjectMapper mapper = new ObjectMapper();

    private final String CUSTOMER_BASE_URL = "http://localhost:7001/api/v1/customers";
    //check in application.yml what is yours for customers

    private final String ORDER_BASE_URL = "api/v1/customers";

    @BeforeEach
    public void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }
    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this); // If not using a runner or extension that does this for you
    }

    @Test
    public void whenGetOrderById_thenReturnsOrder() throws URISyntaxException, JsonProcessingException {

        //Arrange

        var customerModel = CustomerModel.builder()
                .customerId("001")
                .customerFName("John")
                .customerLName("Doe")
                .number("313-321-312")
                .email("email@gmail.com")
                .customerStatus(CustomerStatus.AVAILABLE)
                .build();

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI(CUSTOMER_BASE_URL + "/001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customerModel))
                );

        //find the saleId
        List<Order> orders = orderRepository.findOrdersByCustomerModel_CustomerId(customerModel.getCustomerId());
        Order order = orders.stream()
                .findFirst()
                .get();

        assertNotNull(order);

        String url = ORDER_BASE_URL + "/" + customerModel.getCustomerId() + "/orders/" + order.getOrderIdentifier().getOrderId();

        //Act and assert

        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderResponseDTO.class)
                .value((response)->{
                    assertNotNull(response);
                    assertEquals(order.getOrderIdentifier().getOrderId(), response.getOrderId());
                });
    }



}