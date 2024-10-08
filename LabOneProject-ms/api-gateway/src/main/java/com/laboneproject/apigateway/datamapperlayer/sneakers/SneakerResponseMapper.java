package com.laboneproject.apigateway.datamapperlayer.sneakers;

import com.laboneproject.apigateway.presentationlayer.customers.CustomerController;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.sneakers.SneakerController;
import com.laboneproject.apigateway.presentationlayer.sneakers.SneakerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.sneakers.SneakerResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;


import java.util.List;



@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface SneakerResponseMapper {

    SneakerResponseDTO responseModelToResponseModel(SneakerResponseDTO sneakerResponseDTO);

    List<SneakerResponseDTO> responseModelListToReposeList(List<SneakerResponseDTO> sneakerResponseDTOList);

    /*
    @AfterMapping
    default void addLinks(@MappingTarget SneakerResponseDTO sneakerResponseDTO){

        //do a single link

        Link selfLink = linkTo(methodOn(SneakerController.class)
                .getSneakerBySneakerId(sneakerResponseDTO.getSneakerId()))
                .withSelfRel();

        sneakerResponseDTO.add(selfLink);


        Link allLink = linkTo(methodOn(SneakerController.class)
                .getAllSneakers())
                .withRel("all sneakers");

        sneakerResponseDTO.add(allLink);

    }

     */

}