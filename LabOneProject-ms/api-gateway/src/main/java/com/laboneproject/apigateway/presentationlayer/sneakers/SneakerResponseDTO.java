package com.laboneproject.apigateway.presentationlayer.sneakers;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@EqualsAndHashCode(callSuper=true)
public class SneakerResponseDTO  {

    private String sneakerId;
    private String model;
    private String price;
    private Integer size;
    private String color;
    private String releaseYear;
    private String availableStore;
    private String description;
    private String type;

}
