package com.laboneproject.sneakers.presentationlayer;

import com.laboneproject.sneakers.businesslayer.SneakerService;
import com.laboneproject.sneakers.utils.exceptions.InUseException;
import com.laboneproject.sneakers.utils.exceptions.InvalidInputException;
import com.laboneproject.sneakers.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = SneakerController.class)
@AutoConfigureMockMvc
class SneakerControllerUnitTest {


    private final String FOUND_SNEAKER_ID = "032";

    private final String NOT_FOUND_SNEAKER_ID = "042";

    private final String INVALID_SNEAKER_ID = "000";

    private final String URI_SNEAKERS = "/api/v1/sneakers";

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    SneakerController sneakerController;

    @MockBean
    private SneakerService sneakerService;


    @Test
    public void whenSneakerDoNotExists_ReturnEmptyList(){

        //arrange

        when(sneakerService.getAllSneakers()).thenReturn(Collections.emptyList());

        //act
        ResponseEntity<List<SneakerResponseDTO>> responseEntity = sneakerController.getAllSneakers();

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(sneakerService, times(1)).getAllSneakers();
    }

    @Test
    public void whenSneakerExists_thenReturnSneaker(){
        //arrange

        SneakerRequestDTO sneakerRequestDTO= SneakerRequestDTO.builder().build();
        SneakerResponseDTO sneakerResponseDTO= SneakerResponseDTO.builder().build();

        when(sneakerService.addSneaker(sneakerRequestDTO)).thenReturn(sneakerResponseDTO);

        //act
        ResponseEntity<SneakerResponseDTO> responseEntity= sneakerController.addSneaker(sneakerRequestDTO);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(sneakerResponseDTO, responseEntity.getBody());
        verify(sneakerService, times(1)).addSneaker(sneakerRequestDTO);
    }

    @Test
    public void whenSneakerExist_thenReturnUpdatedSneaker() throws NotFoundException {

        SneakerRequestDTO sneakerRequestDTO = new SneakerRequestDTO();
        SneakerResponseDTO sneakerResponseDTO = SneakerResponseDTO.builder().sneakerId(FOUND_SNEAKER_ID).build();

        when(sneakerService.updateSneaker(sneakerRequestDTO, FOUND_SNEAKER_ID)).thenReturn(sneakerResponseDTO);

        // Act
        ResponseEntity<SneakerResponseDTO> responseEntity = sneakerController.updateSneaker(sneakerRequestDTO, FOUND_SNEAKER_ID);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(FOUND_SNEAKER_ID, responseEntity.getBody().getSneakerId());
        verify(sneakerService, times(1)).updateSneaker(sneakerRequestDTO, FOUND_SNEAKER_ID);

    }

    @Test
    public void whenDeleteSneaker_thenStatusNoContent() throws Exception {
        doNothing().when(sneakerService).deleteSneaker(FOUND_SNEAKER_ID);

        mockMvc.perform(delete("/api/v1/sneakers/{sneakerId}", FOUND_SNEAKER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenGetNonExistentSneaker_thenThrowNotFoundException() throws Exception {
        String nonExistentId = "nonExistentId";
        when(sneakerService.getSneakerBySneakerId(nonExistentId))
                .thenThrow(new NotFoundException("Sneaker not found"));

        mockMvc.perform(get("/sneakers/{id}", nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenAddSneakerWithInvalidInput_thenThrowInvalidInputException() {
        SneakerRequestDTO invalidSneakerDTO = new SneakerRequestDTO("butush","1000",1,"white","2000","Nike","des","sneak");
        doThrow(new InvalidInputException("Invalid input")).when(sneakerService).addSneaker(invalidSneakerDTO);

        assertThrows(InvalidInputException.class, () -> sneakerController.addSneaker(invalidSneakerDTO));
    }

    @Test
    public void whenDeleteSneakerInUse_thenThrowInUseException() {
        String inUseSneakerId = "inUseId";
        doThrow(new InUseException("Sneaker is currently in use")).when(sneakerService).deleteSneaker(inUseSneakerId);

        assertThrows(InUseException.class, () -> sneakerController.deleteSneaker(inUseSneakerId));
    }

}