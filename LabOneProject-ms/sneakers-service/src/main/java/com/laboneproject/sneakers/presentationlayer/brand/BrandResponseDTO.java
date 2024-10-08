package com.laboneproject.sneakers.presentationlayer.brand;

import com.laboneproject.sneakers.dataaccesslayer.brand.Founder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class BrandResponseDTO extends RepresentationModel<BrandResponseDTO> {

    private String brandId;
    private String name;
    private String associatedCelebrity;
    private String locationOfMainHeadquarters;
    private String description;
    private List<Founder> founders;
}


