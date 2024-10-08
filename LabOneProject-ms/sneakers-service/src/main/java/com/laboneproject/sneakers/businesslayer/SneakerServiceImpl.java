package com.laboneproject.sneakers.businesslayer;



import com.laboneproject.sneakers.dataaccesslayer.Sneaker;
import com.laboneproject.sneakers.dataaccesslayer.SneakerIdentifier;
import com.laboneproject.sneakers.dataaccesslayer.SneakerRepository;
import com.laboneproject.sneakers.dataaccesslayer.brand.Brand;
import com.laboneproject.sneakers.datamapperlayer.SneakerRequestMapper;
import com.laboneproject.sneakers.datamapperlayer.SneakerResponseMapper;
import com.laboneproject.sneakers.presentationlayer.SneakerRequestDTO;
import com.laboneproject.sneakers.presentationlayer.SneakerResponseDTO;
import com.laboneproject.sneakers.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SneakerServiceImpl implements SneakerService{

    private final SneakerRepository sneakerRepository;
    private final SneakerResponseMapper sneakerResponseMapper;
    private final SneakerRequestMapper sneakerRequestMapper;

    public SneakerServiceImpl(SneakerRepository sneakerRepository, SneakerResponseMapper sneakerResponseMapper, SneakerRequestMapper sneakerRequestMapper) {
        this.sneakerRepository = sneakerRepository;
        this.sneakerResponseMapper = sneakerResponseMapper;
        this.sneakerRequestMapper = sneakerRequestMapper;
    }


    @Override
    public List<SneakerResponseDTO> getAllSneakers() {

        List<Sneaker> sneaker = sneakerRepository.findAll();
        return sneakerResponseMapper.entityListToResponseDTOList(sneaker);
    }

    @Override
    public SneakerResponseDTO getSneakerBySneakerId(String sneakerId) {

        Sneaker sneaker = sneakerRepository.findSneakerBySneakerIdentifier_SneakerId(sneakerId);

        if (sneaker == null) {
            throw new NotFoundException("Unknown sneakerId: " + sneakerId);
        }
        return sneakerResponseMapper.entityToResponseDTO(sneaker);
    }

    @Override
    public SneakerResponseDTO addSneaker(SneakerRequestDTO sneakerRequestDTO) {

        Sneaker sneaker = sneakerRequestMapper.requestDTOToEntity(sneakerRequestDTO, new SneakerIdentifier());
        return sneakerResponseMapper.entityToResponseDTO(sneakerRepository.save(sneaker));
    }

    @Override
    public SneakerResponseDTO updateSneaker(SneakerRequestDTO sneakerRequestDTO, String sneakerId) {

        Sneaker foundSneaker = sneakerRepository.findSneakerBySneakerIdentifier_SneakerId(sneakerId);

        Sneaker updatedSneaker = sneakerRequestMapper.requestDTOToEntity(sneakerRequestDTO, foundSneaker.getSneakerIdentifier());
        updatedSneaker.setId(foundSneaker.getId());
        return sneakerResponseMapper.entityToResponseDTO(sneakerRepository.save(updatedSneaker));
    }

    @Override
    public void deleteSneaker(String sneakerId) {

        Sneaker sneaker = sneakerRepository.findSneakerBySneakerIdentifier_SneakerId(sneakerId);

        sneakerRepository.delete(sneaker);
    }
}
