package com.laboneproject.sneakers.presentationlayer;


import com.laboneproject.sneakers.dataaccesslayer.brand.Brand;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SneakerResponseDTO extends RepresentationModel<SneakerResponseDTO>{

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



