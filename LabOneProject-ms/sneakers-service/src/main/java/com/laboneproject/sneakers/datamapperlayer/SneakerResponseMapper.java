package com.laboneproject.sneakers.datamapperlayer;



import com.laboneproject.sneakers.dataaccesslayer.Sneaker;
import com.laboneproject.sneakers.presentationlayer.SneakerController;
import com.laboneproject.sneakers.presentationlayer.SneakerResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Mapper(componentModel = "spring")
public interface SneakerResponseMapper {


    @Mapping(expression = "java(sneaker.getSneakerIdentifier().getSneakerId())", target = "sneakerId")

    SneakerResponseDTO entityToResponseDTO(Sneaker sneaker);

    List<SneakerResponseDTO> entityListToResponseDTOList(List<Sneaker> sneakerList);


    @AfterMapping
    default void addLinks(@MappingTarget SneakerResponseDTO sneakerResponseDTO, Sneaker sneaker) {
        //self link
        Link selfLink = linkTo(methodOn(SneakerController.class).getSneakerById(sneakerResponseDTO.getSneakerId())).withSelfRel();
        sneakerResponseDTO.add(selfLink);


        //all inventories link
        Link sneakersLink = linkTo(methodOn(SneakerController.class).getAllSneakers()).withRel("sneakers");
        sneakerResponseDTO.add(sneakersLink);

    }


}


