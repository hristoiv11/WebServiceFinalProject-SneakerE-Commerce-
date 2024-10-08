package com.laboneproject.sneakers.presentationlayer;

import com.laboneproject.sneakers.businesslayer.SneakerService;
import com.laboneproject.sneakers.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/sneakers")
public class SneakerController {

    private final SneakerService sneakerService;

    @Autowired
    public SneakerController(SneakerService sneakerService) {
        this.sneakerService = sneakerService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SneakerResponseDTO>> getAllSneakers() {
        return ResponseEntity.ok().body(sneakerService.getAllSneakers());
    }

    @GetMapping(value ="/{sneakerId}",produces = "application/json")
    public ResponseEntity<SneakerResponseDTO> getSneakerById(@PathVariable String sneakerId) {
        SneakerResponseDTO sneakerResponseDTO = sneakerService.getSneakerBySneakerId(sneakerId);
        return ResponseEntity.status(HttpStatus.OK).body(sneakerResponseDTO);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<SneakerResponseDTO> addSneaker(@RequestBody SneakerRequestDTO sneakerRequestDTO) {
        SneakerResponseDTO addedSneaker = sneakerService.addSneaker(sneakerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedSneaker);
    }

    @PutMapping(value ="/{sneakerId}",produces = "application/json")
    public ResponseEntity<SneakerResponseDTO> updateSneaker(
            @RequestBody SneakerRequestDTO sneakerRequestDTO,
            @PathVariable String sneakerId) {
        SneakerResponseDTO updatedSneaker = sneakerService.updateSneaker(sneakerRequestDTO, sneakerId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSneaker);
    }

    @DeleteMapping("/{sneakerId}")
    public ResponseEntity<Void> deleteSneaker(@PathVariable String sneakerId) {
        sneakerService.deleteSneaker(sneakerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

