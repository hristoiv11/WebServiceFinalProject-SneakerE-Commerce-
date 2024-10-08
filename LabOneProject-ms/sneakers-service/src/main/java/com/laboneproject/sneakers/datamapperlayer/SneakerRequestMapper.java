package com.laboneproject.sneakers.datamapperlayer;


import com.laboneproject.sneakers.dataaccesslayer.Sneaker;
import com.laboneproject.sneakers.dataaccesslayer.SneakerIdentifier;
import com.laboneproject.sneakers.dataaccesslayer.brand.Brand;
import com.laboneproject.sneakers.dataaccesslayer.brand.BrandIdentifier;
import com.laboneproject.sneakers.presentationlayer.SneakerRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface SneakerRequestMapper {

    @Mapping(target = "id", ignore= true)
    Sneaker requestDTOToEntity(SneakerRequestDTO sneakerRequestDTO,
                               SneakerIdentifier sneakerIdentifier);
}



