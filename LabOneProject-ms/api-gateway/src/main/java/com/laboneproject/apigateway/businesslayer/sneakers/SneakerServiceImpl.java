package com.laboneproject.apigateway.businesslayer.sneakers;

import com.laboneproject.apigateway.businesslayer.customers.CustomerService;
import com.laboneproject.apigateway.dataaccesslayer.customers.CustomersServiceClient;
import com.laboneproject.apigateway.dataaccesslayer.sneakers.SneakerServiceClient;
import com.laboneproject.apigateway.datamapperlayer.customers.CustomerResponseMapper;
import com.laboneproject.apigateway.datamapperlayer.sneakers.SneakerResponseMapper;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.sneakers.SneakerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.sneakers.SneakerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SneakerServiceImpl implements SneakerService {

    private final SneakerServiceClient sneakerServiceClient;
    private final SneakerResponseMapper sneakerResponseMapper;

    public SneakerServiceImpl(SneakerServiceClient sneakerServiceClient, SneakerResponseMapper sneakerResponseMapper) {
        this.sneakerServiceClient = sneakerServiceClient;
        this.sneakerResponseMapper = sneakerResponseMapper;
    }

    @Override
    public List<SneakerResponseDTO> getAllSneakers() {
        return sneakerResponseMapper.responseModelListToReposeList(sneakerServiceClient.getAllSneakers());
    }

    @Override
    public SneakerResponseDTO getSneakerBySneakerId(String sneakerId) {
        return sneakerResponseMapper.responseModelToResponseModel(sneakerServiceClient.getSneakerBySneakerId(sneakerId));
    }

    @Override
    public SneakerResponseDTO addSneaker(SneakerRequestDTO sneakerRequestDTO) {
        return sneakerResponseMapper.responseModelToResponseModel(sneakerServiceClient.addSneaker(sneakerRequestDTO));
    }

    @Override
    public SneakerResponseDTO updateSneaker(SneakerRequestDTO sneakerRequestDTO, String sneakerId) {
        return sneakerResponseMapper.responseModelToResponseModel(sneakerServiceClient.updateSneaker(sneakerId,sneakerRequestDTO));
    }

    @Override
    public void deleteSneaker(String sneakerId) {
        sneakerServiceClient.deleteSneaker(sneakerId);
    }


}

