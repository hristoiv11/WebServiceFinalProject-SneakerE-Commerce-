package com.laboneproject.sneakers.dataaccesslayer.brand;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "brands")
@Data
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private BrandIdentifier brandIdentifier;

    private String name;
    private String associatedCelebrity;
    private String locationOfMainHeadquarters;
    private String description;

    @ElementCollection(fetch= FetchType.LAZY)
    @CollectionTable(name= "brand_founders", joinColumns = @JoinColumn(name="brand_Id"))
    private List<Founder> founders;

    public Brand(@NotNull String name, @NotNull String associatedCelebrity, @NotNull String locationOfMainHeadquarters, @NotNull String description, @NotNull List<Founder> founders) {
        this.brandIdentifier = new BrandIdentifier();
        this.name = name;
        this.associatedCelebrity = associatedCelebrity;
        this.locationOfMainHeadquarters = locationOfMainHeadquarters;
        this.description = description;
        this.founders = founders;
    }

    public Brand() {

    }
}

