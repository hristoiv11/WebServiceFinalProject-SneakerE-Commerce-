package com.laboneproject.sneakers.businesslayer;




import com.laboneproject.sneakers.presentationlayer.SneakerRequestDTO;
import com.laboneproject.sneakers.presentationlayer.SneakerResponseDTO;

import java.util.List;


public interface SneakerService {

    List<SneakerResponseDTO> getAllSneakers();

    SneakerResponseDTO getSneakerBySneakerId(String sneakerId);

    SneakerResponseDTO addSneaker(SneakerRequestDTO sneakerRequestDTO);

    SneakerResponseDTO updateSneaker(SneakerRequestDTO sneakerRequestDTO, String sneakerId);

    void deleteSneaker(String sneakerId);
}
