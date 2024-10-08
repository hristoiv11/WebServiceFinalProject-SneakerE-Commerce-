package com.laboneproject.inventory.dataaccesslayer;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@Getter
@NoArgsConstructor
public class StockLevel {

    private String availableLevel;
    private String restockLevel;

    public StockLevel(String availableLevel, String restockLevel) {
        this.availableLevel = availableLevel;
        this.restockLevel = restockLevel;
    }
}


