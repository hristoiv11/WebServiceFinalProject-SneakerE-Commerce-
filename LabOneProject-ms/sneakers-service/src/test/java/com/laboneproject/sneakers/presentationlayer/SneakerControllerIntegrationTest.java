package com.laboneproject.sneakers.presentationlayer;

import com.laboneproject.sneakers.dataaccesslayer.SneakerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SneakerControllerIntegrationTest {

    @Autowired
    private SneakerRepository sneakerRepository;

    @Autowired
    private WebTestClient webTestClient;

    private final String URI_SNEAKERS = "/api/v1/sneakers";

    private final String FOUND_SNEAKER_ID = "040";

    private final String NOT_FOUND_SNEAKER_ID = "050";



    @Test
    public void whenGetSneakers_thenReturnAllSneakers(){

        //arrange
        long sizeDB = sneakerRepository.count();

        //act and assert
        webTestClient.get()
                .uri(URI_SNEAKERS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(SneakerResponseDTO.class)
                .value((list) -> {
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                });
    }

    @Test
    public void whenGetSneakerDoesNotExist_thenReturnNotFound(){

        //act and assert
        webTestClient.get()
                .uri(URI_SNEAKERS + "/" + NOT_FOUND_SNEAKER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown sneakerId: " + NOT_FOUND_SNEAKER_ID);

    }

    @Test
    public void whenGetSneakerByIdExists_thenReturnSneakerId(){

        webTestClient.get().uri(URI_SNEAKERS + "/{sneakerId}", FOUND_SNEAKER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.sneakerId").isEqualTo(FOUND_SNEAKER_ID)
                .jsonPath("$.model").isEqualTo("Nike 270")
                .jsonPath("$.price").isEqualTo("100")
                .jsonPath("$.size").isEqualTo(8)
                .jsonPath("$.color").isEqualTo("white and blue")
                .jsonPath("$.releaseYear").isEqualTo("2019")
                .jsonPath("$.availableStore").isEqualTo("Nike")
                .jsonPath("$.description").isEqualTo("build with high quality materials")
                .jsonPath("$.type").isEqualTo("sneaker");

    }


    @Test
    public void whenCreateSneaker_thenStatusCreated() {
        SneakerRequestDTO sneakerRequestDTO = new SneakerRequestDTO("Nike Dunk", "220", 9, "red", "2020", "Nike Store", "High quality", "sneaker");

        webTestClient.post().uri(URI_SNEAKERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(sneakerRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.model").isEqualTo("Nike Dunk")
                .jsonPath("$.price").isEqualTo("220")
                .jsonPath("$.size").isEqualTo(9)
                .jsonPath("$.color").isEqualTo("red")
                .jsonPath("$.releaseYear").isEqualTo("2020")
                .jsonPath("$.availableStore").isEqualTo("Nike Store")
                .jsonPath("$.description").isEqualTo("High quality")
                .jsonPath("$.type").isEqualTo("sneaker");

    }

    @Test
    public void whenUpdateSneaker_thenStatusOk() {

        String existingSneakerId = "038";

        SneakerRequestDTO updatedSneaker = new SneakerRequestDTO("Nike air max 90", "190", 8, "black", "2012", "Nike Store", "High quality", "boots");

        webTestClient.put().uri(URI_SNEAKERS + "/{sneakerId}", existingSneakerId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedSneaker)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.model").isEqualTo("Nike air max 90")
                .jsonPath("$.price").isEqualTo("190")
                .jsonPath("$.size").isEqualTo(8)
                .jsonPath("$.color").isEqualTo("black")
                .jsonPath("$.releaseYear").isEqualTo("2012")
                .jsonPath("$.availableStore").isEqualTo("Nike Store")
                .jsonPath("$.description").isEqualTo("High quality")
                .jsonPath("$.type").isEqualTo("boots");

    }

    @Test
    public void whenDeleteSneaker_thenStatusNoContent() {
        webTestClient.delete().uri(URI_SNEAKERS + "/{sneakerId}", FOUND_SNEAKER_ID)
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    void whenGetSneakers_thenStatusOk() {
        webTestClient.get().uri(URI_SNEAKERS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(SneakerResponseDTO.class);
    }

}