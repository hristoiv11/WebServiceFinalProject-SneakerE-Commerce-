package com.laboneproject.sneakers.datamapperlayer.brand;



import com.laboneproject.sneakers.dataaccesslayer.SneakerIdentifier;
import com.laboneproject.sneakers.dataaccesslayer.brand.Brand;
import com.laboneproject.sneakers.dataaccesslayer.brand.BrandIdentifier;
import com.laboneproject.sneakers.presentationlayer.brand.BrandRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BrandRequestMapper {

    @Mapping(target = "id", ignore= true)
    Brand requestDTOToEntity(BrandRequestDTO brandRequestDTO,
                             BrandIdentifier brandIdentifier);
}

