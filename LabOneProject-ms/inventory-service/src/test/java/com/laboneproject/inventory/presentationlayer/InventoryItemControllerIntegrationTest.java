package com.laboneproject.inventory.presentationlayer;

import com.laboneproject.inventory.dataaccesslayer.InventoryItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class InventoryItemControllerIntegrationTest {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private WebTestClient webTestClient;

    private final String URI_INVENTORY = "/api/v1/inventories";

    private final String FOUND_INVENTORY_ID = "011";

    private final String NOT_FOUND_INVENTORY_ID = "050";



    @Test
    public void whenGetInventories_thenReturnAllInventories(){

        //arrange
        long sizeDB = inventoryItemRepository.count();

        //act and assert
        webTestClient.get()
                .uri(URI_INVENTORY)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(InventoryItemResponseDTO.class)
                .value((list) -> {
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                });
    }

    @Test
    public void whenGetInventoryDoesNotExist_thenReturnNotFound(){

        //act and assert
        webTestClient.get()
                .uri(URI_INVENTORY + "/" + NOT_FOUND_INVENTORY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown inventoryId: " + NOT_FOUND_INVENTORY_ID);

    }


    @Test
    public void whenGetInventoryByIdExists_thenReturnInventoryId(){

        webTestClient.get().uri(URI_INVENTORY + "/{inventoryId}", FOUND_INVENTORY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.inventoryId").isEqualTo(FOUND_INVENTORY_ID)
                .jsonPath("$.availableLevel").isEqualTo("3")
                .jsonPath("$.restockLevel").isEqualTo("10");
    }


    @Test
    public void whenCreateInventory_thenStatusCreated() {
        InventoryItemRequestDTO inventoryItemRequestDTO = new InventoryItemRequestDTO("15","15");

        webTestClient.post().uri(URI_INVENTORY)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryItemRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.availableLevel").isEqualTo("15")
                .jsonPath("$.restockLevel").isEqualTo("15");

    }

    @Test
    public void whenUpdateInventory_thenStatusOk() {

        String existingInventoryId = "015";

        InventoryItemRequestDTO updatedInventory = new InventoryItemRequestDTO("1","4");

        webTestClient.put().uri(URI_INVENTORY + "/{inventoryId}", existingInventoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedInventory)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.availableLevel").isEqualTo("1")
                .jsonPath("$.restockLevel").isEqualTo("4");

    }

    @Test
    public void whenDeleteInventory_thenStatusNoContent() {
        webTestClient.delete().uri(URI_INVENTORY + "/{inventoryId}", FOUND_INVENTORY_ID)
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    void whenGetInventories_thenStatusOk() {
        webTestClient.get().uri(URI_INVENTORY)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(InventoryItemResponseDTO.class);
    }

}



