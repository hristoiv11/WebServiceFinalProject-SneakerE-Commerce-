package com.laboneproject.sneakers.dataaccesslayer.brand;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Embeddable
@Getter
@NoArgsConstructor
public class Founder {

    private String fName;
    private String lName;
    private LocalDate dob;
    private String country;

    /*
    public Founder(@NotNull String fName,@NotNull String lName,@NotNull LocalDate dob,@NotNull String country) {
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.country = country;
    }

     */
}
