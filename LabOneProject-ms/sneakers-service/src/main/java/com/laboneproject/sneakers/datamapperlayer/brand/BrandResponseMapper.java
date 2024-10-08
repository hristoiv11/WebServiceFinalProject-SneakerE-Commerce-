package com.laboneproject.sneakers.datamapperlayer.brand;



import com.laboneproject.sneakers.dataaccesslayer.brand.Brand;
import com.laboneproject.sneakers.presentationlayer.brand.BrandController;
import com.laboneproject.sneakers.presentationlayer.brand.BrandResponseDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Mapper(componentModel = "spring")
public interface BrandResponseMapper {

    @Mapping(expression = "java(brand.getBrandIdentifier().getBrandId())", target = "brandId")
    BrandResponseDTO entityToResponseDTO(Brand brand);

    List<BrandResponseDTO> entityListToResponseDTOList(List<Brand> brandList);

    @AfterMapping
    default void addLinks(@MappingTarget BrandResponseDTO brandResponseDTO, Brand brand) {
        //self link
        Link selfLink = linkTo(methodOn(BrandController.class).getBrandById(brandResponseDTO.getBrandId())).withSelfRel();
        brandResponseDTO.add(selfLink);


        //all inventories link
        Link inventoriesLink = linkTo(methodOn(BrandController.class).getAllBrands()).withRel("brands");
        brandResponseDTO.add(inventoriesLink);

    }
}

