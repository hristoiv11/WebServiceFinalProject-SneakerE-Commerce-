package com.laboneproject.apigateway.presentationlayer.sneakers;

import com.laboneproject.apigateway.businesslayer.customers.CustomerService;
import com.laboneproject.apigateway.businesslayer.sneakers.SneakerService;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/sneakers")
public class SneakerController {

    private final SneakerService sneakerService;

    @Autowired
    public SneakerController(SneakerService sneakerService)   {
        this.sneakerService = sneakerService;
    }

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<SneakerResponseDTO>> getAllSneakers() {
        return ResponseEntity.ok().body(sneakerService.getAllSneakers());
    }

    @GetMapping(

            value ="/{sneakerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SneakerResponseDTO> getSneakerBySneakerId(@PathVariable String sneakerId) {
        SneakerResponseDTO sneakerResponseDTO = sneakerService.getSneakerBySneakerId(sneakerId);

        return ResponseEntity.status(HttpStatus.OK).body(sneakerResponseDTO);
    }


    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SneakerResponseDTO> addSneaker(@RequestBody SneakerRequestDTO sneakerRequestDTO) {
        SneakerResponseDTO addedSneaker = sneakerService.addSneaker(sneakerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedSneaker);
    }

    @PutMapping(

            value = "/{sneakerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SneakerResponseDTO> updateSneaker(
            @RequestBody SneakerRequestDTO sneakerRequestDTO,
            @PathVariable String sneakerId) {
        SneakerResponseDTO updatedSneaker = sneakerService.updateSneaker(sneakerRequestDTO, sneakerId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedSneaker);
    }

    @DeleteMapping(

            value = "/{sneakerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteSneaker(@PathVariable String sneakerId) {
        sneakerService.deleteSneaker(sneakerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
