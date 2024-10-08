package com.laboneproject.customers.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    
    private String customerPreferredSneaker;
    private String customerPreferredBrand;
    private String customerPreferredSize;

}
