package com.laboneproject.sneakers.presentationlayer.brand;

import com.laboneproject.sneakers.dataaccesslayer.brand.Founder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BrandRequestDTO {

    private String name;
    private String associatedCelebrity;
    private String locationOfMainHeadquarters;
    private String description;
    private List<Founder> founders;
}


