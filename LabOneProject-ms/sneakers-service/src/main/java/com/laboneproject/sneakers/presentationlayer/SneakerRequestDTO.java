package com.laboneproject.sneakers.presentationlayer;

import com.laboneproject.sneakers.dataaccesslayer.brand.Brand;
import com.laboneproject.sneakers.dataaccesslayer.brand.BrandIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SneakerRequestDTO {

    private String model;
    private String price;
    private Integer size;
    private String color;
    private String releaseYear;
    private String availableStore;
    private String description;
    private String type;


}


