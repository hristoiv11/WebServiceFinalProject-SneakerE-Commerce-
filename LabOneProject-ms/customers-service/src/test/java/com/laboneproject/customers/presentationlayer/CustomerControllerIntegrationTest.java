package com.laboneproject.customers.presentationlayer;


import com.laboneproject.customers.dataaccesslayer.CustomerRepository;
import com.laboneproject.customers.dataaccesslayer.CustomerStatus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WebTestClient webTestClient;

    private final String URI_CUSTOMERS = "/api/v1/customers";

    private final String FOUND_CUSTOMER_ID = "005";

    private final String NOT_FOUND_CUSTOMER_ID = "050";


    @Test
    public void whenGetCustomers_thenReturnAllCustomers(){

        //arrange
        long sizeDB = customerRepository.count();

        //act and assert
        webTestClient.get()
                .uri(URI_CUSTOMERS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerResponseDTO.class)
                .value((list) -> {
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                });
    }

    @Test
    public void whenGetCustomerDoesNotExist_thenReturnNotFound(){

        //act and assert
        webTestClient.get()
                .uri(URI_CUSTOMERS + "/" + NOT_FOUND_CUSTOMER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown customerId: " + NOT_FOUND_CUSTOMER_ID);

    }

    @Test
    public void whenGetCustomerByIdExists_thenReturnCustomerId(){

        webTestClient.get().uri(URI_CUSTOMERS + "/{customerId}", FOUND_CUSTOMER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.customerId").isEqualTo(FOUND_CUSTOMER_ID)
                .jsonPath("$.customerFName").isEqualTo("Hristo")
                .jsonPath("$.customerLName").isEqualTo("Ivanov")
                .jsonPath("$.number").isEqualTo("313-321-123")
                .jsonPath("$.email").isEqualTo("email@gmail.com")
                .jsonPath("$.customerPreferredSneaker").isEqualTo("adidas campus")
                .jsonPath("$.customerPreferredBrand").isEqualTo("adidas")
                .jsonPath("$.customerPreferredSize").isEqualTo("13")
                .jsonPath("$.customerStatus").isEqualTo("AVAILABLE");


    }


    @Test
    public void whenCreateCustomer_thenStatusCreated() {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO("Petio","Dragov","213-313-123"
                ,"email@gmail.com","New Balance 2002r","new balance","8", CustomerStatus.AVAILABLE);

        webTestClient.post().uri(URI_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.customerFName").isEqualTo("Petio")
                .jsonPath("$.customerLName").isEqualTo("Dragov")
                .jsonPath("$.number").isEqualTo("213-313-123")
                .jsonPath("$.email").isEqualTo("email@gmail.com")
                .jsonPath("$.customerPreferredSneaker").isEqualTo("New Balance 2002r")
                .jsonPath("$.customerPreferredBrand").isEqualTo("new balance")
                .jsonPath("$.customerPreferredSize").isEqualTo("8")
                .jsonPath("$.customerStatus").isEqualTo("AVAILABLE");

    }

    @Test
    public void whenUpdateCustomer_thenStatusOk() {

        String existingCustomerId = "008";

        CustomerRequestDTO updatedCustomer = new CustomerRequestDTO("Jack", "Gradle", "313-321-786", "email@gmail.com", "nike air max 90", "nike", "8", CustomerStatus.AVAILABLE);

        webTestClient.put().uri(URI_CUSTOMERS + "/{customerId}", existingCustomerId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedCustomer)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.customerFName").isEqualTo("Jack")
                .jsonPath("$.customerLName").isEqualTo("Gradle")
                .jsonPath("$.number").isEqualTo("313-321-786")
                .jsonPath("$.email").isEqualTo("email@gmail.com")
                .jsonPath("$.customerPreferredSneaker").isEqualTo("nike air max 90")
                .jsonPath("$.customerPreferredBrand").isEqualTo("nike")
                .jsonPath("$.customerPreferredSize").isEqualTo("8")
                .jsonPath("$.customerStatus").isEqualTo("AVAILABLE");

    }

    @Test
    public void whenDeleteCustomer_thenStatusNoContent() {
        webTestClient.delete().uri(URI_CUSTOMERS + "/{customerId}", FOUND_CUSTOMER_ID)
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    void whenGetCustomers_thenStatusOk() {
        webTestClient.get().uri(URI_CUSTOMERS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerResponseDTO.class);
    }


    /*
    @Test
    public void whenNotFoundExceptionThrown_thenReturnNotFoundResponse() {
        // Arrange - You need to set up a scenario where this exception will be thrown.
        // This could be when trying to find a customer that doesn't exist.
        String nonExistentCustomerId = "nonExistentId";
        when(customerService.getCustomerById(nonExistentCustomerId))
                .thenThrow(new NotFoundException("Unknown customerId"));

        // Act & Assert
        webTestClient.get().uri(URI_CUSTOMERS + "/{customerId}", nonExistentCustomerId)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(HttpErrorInfo.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertEquals(NOT_FOUND, response.getResponseBody().getHttpStatus());
                    assertTrue(response.getResponseBody().getPath().contains(nonExistentCustomerId));
                    assertEquals("Unknown customerId", response.getResponseBody().getMessage());
                });
    }


     */
    /*
    @Test
    public void whenInUseExceptionThrown_thenReturnUnprocessableEntityResponse() {
        // Arrange - You need to set up a scenario where this exception will be thrown.
        // This could be when trying to delete a customer that is currently in use.
        String inUseCustomerId = "inUseId";
        doThrow(new InUseException("Customer is currently in use"))
                .when(customerService).deleteCustomer(inUseCustomerId);

        // Act & Assert
        webTestClient.delete().uri(URI_CUSTOMERS + "/{customerId}", inUseCustomerId)
                .exchange()
                .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
                .expectBody(HttpErrorInfo.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertEquals(UNPROCESSABLE_ENTITY, response.getResponseBody().getHttpStatus());
                    assertTrue(response.getResponseBody().getPath().contains(inUseCustomerId));
                    assertEquals("Customer is currently in use", response.getResponseBody().getMessage());
                });
    }


     */

}
