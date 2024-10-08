package com.laboneproject.sneakers.dataaccesslayer.brand;


import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class BrandIdentifier {

    private String brandId;

    public BrandIdentifier(){
        this.brandId= UUID.randomUUID().toString();
    }

}


