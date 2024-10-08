package com.laboneproject.customers.dataaccesslayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="customers")
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    CustomerIdentifier customerIdentifier;

    private String customerFName;
    private String customerLName;
    private String number;
    private String email;

    @Embedded
    private Wishlist wishlist;

    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;

    public Customer(@NotNull String customerFName,@NotNull String customerLName,@NotNull String number,@NotNull String email,@NotNull Wishlist wishlist,@NotNull CustomerStatus customerStatus) {
        this.customerIdentifier = new CustomerIdentifier();
        this.customerFName= customerFName;
        this.customerLName = customerLName;
        this.number = number;
        this.email = email;
        this.wishlist = wishlist;
        this.customerStatus=customerStatus;
    }

}
