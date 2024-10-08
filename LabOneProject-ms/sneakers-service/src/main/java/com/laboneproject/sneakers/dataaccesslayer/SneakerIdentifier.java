package com.laboneproject.sneakers.dataaccesslayer;


import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class SneakerIdentifier {

    private String sneakerId;
    public SneakerIdentifier(){
        this.sneakerId= UUID.randomUUID().toString();
    }

    /*
    public SneakerIdentifier(String sneakerId) {
        this.sneakerId = sneakerId;
    }

     */

}


