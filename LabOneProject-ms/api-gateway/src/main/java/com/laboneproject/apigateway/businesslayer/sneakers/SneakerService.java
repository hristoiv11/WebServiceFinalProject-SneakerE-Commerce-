package com.laboneproject.apigateway.businesslayer.sneakers;

import com.laboneproject.apigateway.presentationlayer.orders.OrderRequestDTO;
import com.laboneproject.apigateway.presentationlayer.orders.OrderResponseDTO;
import com.laboneproject.apigateway.presentationlayer.sneakers.SneakerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.sneakers.SneakerResponseDTO;

import java.util.List;

public interface SneakerService {

    List<SneakerResponseDTO> getAllSneakers();
    SneakerResponseDTO getSneakerBySneakerId(String sneakerId);
    SneakerResponseDTO addSneaker(SneakerRequestDTO sneakerRequestDTO);
    SneakerResponseDTO updateSneaker(SneakerRequestDTO sneakerRequestDTO, String sneakerId);
    void deleteSneaker(String sneakerId);
}
