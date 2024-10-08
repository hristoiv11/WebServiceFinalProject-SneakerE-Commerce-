package com.laboneproject.apigateway.presentationlayer.sneakers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
