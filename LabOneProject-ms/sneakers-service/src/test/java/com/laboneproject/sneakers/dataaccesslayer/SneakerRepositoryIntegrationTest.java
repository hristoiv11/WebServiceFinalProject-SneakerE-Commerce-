package com.laboneproject.sneakers.dataaccesslayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SneakerRepositoryIntegrationTest {

    private final String INVALID_SNEAKER_ID = "045";


    @Autowired
    SneakerRepository sneakerRepository;

    @BeforeEach
    public void setupDb(){
        sneakerRepository.deleteAll();
    }


    @Test
    public void whenSneakerExists_thenReturnCorrectSneakerDetails(){
        //arrange
        Sneaker sneaker1= new Sneaker("Nike 270","100",8,"white and blue","2019","Nike","build with high quality materials","sneaker");

        sneakerRepository.save(sneaker1);
        //act
        Sneaker sneaker= sneakerRepository.findSneakerBySneakerIdentifier_SneakerId(sneaker1.getSneakerIdentifier().getSneakerId());

        //assert
        assertNotNull(sneaker);
        assertEquals(sneaker.getSneakerIdentifier().getSneakerId(), sneaker1.getSneakerIdentifier().getSneakerId());
        assertEquals(sneaker.getModel(),sneaker1.getModel());
        assertEquals(sneaker.getPrice(),sneaker1.getPrice());
        assertEquals(sneaker.getSize(),sneaker1.getSize());
        assertEquals(sneaker.getColor(),sneaker1.getColor());
        assertEquals(sneaker.getReleaseYear(),sneaker1.getReleaseYear());
        assertEquals(sneaker.getAvailableStore(),sneaker1.getAvailableStore());
        assertEquals(sneaker.getDescription(),sneaker1.getDescription());
        assertEquals(sneaker.getType(),sneaker1.getType());

    }

    @Test
    public void whenSneakerDoesNotExists_thenReturnNull(){

        Sneaker sneaker = sneakerRepository.findSneakerBySneakerIdentifier_SneakerId(INVALID_SNEAKER_ID);

        assertNull(sneaker,"Invalid sneakerId");
    }

    @Test
    public void whenFindAll_thenReturnAllSneakers() {
        // Arrange
        Sneaker sneaker1 = new Sneaker("Puma", "100", 9, "Black", "2021", "Store1", "Since 1956", "sneaker");
        Sneaker sneaker2 = new Sneaker("Puma2", "100", 13, "white", "2021", "Store2", "Since 1956", "sneaker");
        sneakerRepository.save(sneaker1);
        sneakerRepository.save(sneaker2);

        // Act
        List<Sneaker> allSneakers = sneakerRepository.findAll();

        // Assert
        assertEquals(2, allSneakers.size());
        assertTrue(allSneakers.contains(sneaker1));
        assertTrue(allSneakers.contains(sneaker2));
    }


}