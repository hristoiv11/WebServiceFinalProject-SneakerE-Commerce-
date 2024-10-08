package com.laboneproject.purchases.domainclientlayer.sneakers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class SneakerModel {
    String sneakerId;
    String model;
    String price;
    Integer size;
    String color;
    String releaseYear;
    String availableStore;
    String description;
    String type;

}
