package com.laboneproject.sneakers.dataaccesslayer;


import com.laboneproject.sneakers.dataaccesslayer.brand.Brand;
import com.laboneproject.sneakers.dataaccesslayer.brand.BrandIdentifier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "sneakers")
@Data
@NoArgsConstructor
public class Sneaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private SneakerIdentifier sneakerIdentifier;

    private String model;
    private String price;
    private Integer size;
    private String color;
    private String releaseYear;
    private String availableStore;
    private String description;
    private String type;


    public Sneaker(@NotNull String model,@NotNull String price,@NotNull Integer size,@NotNull String color,@NotNull String releaseYear,@NotNull String availableStore,@NotNull String description,@NotNull String type) {

        this.sneakerIdentifier = new SneakerIdentifier();
        this.model = model;
        this.price = price;
        this.size = size;
        this.color = color;
        this.releaseYear = releaseYear;
        this.availableStore = availableStore;
        this.description = description;
        this.type = type;
    }
}


