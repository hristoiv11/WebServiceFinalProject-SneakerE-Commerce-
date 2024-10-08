package com.laboneproject.purchases.dataaccesslayer;


import com.laboneproject.purchases.domainclientlayer.customers.CustomerModel;
import com.laboneproject.purchases.domainclientlayer.inventories.InventoryModel;
import com.laboneproject.purchases.domainclientlayer.sneakers.SneakerModel;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder(toBuilder = true)
@Document(collection = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Id
    private String id;

    @Indexed(unique = true)
    private OrderIdentifier orderIdentifier;
    private CustomerModel customerModel;
    private SneakerModel sneakerModel;
    private InventoryModel inventoryModel;

    /*
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "shipping_address", joinColumns = @JoinColumn(name="order_id"))

     */
    //private List<ShippingAddress> shippingAddress;
    private ShippingAddress shippingAddress;

    private OrderStatus orderStatus;

    private Integer quantityBought;


    /*
    public Order(@NotNull OrderIdentifier orderIdentifier, @NotNull CustomerModel customerModel, @NotNull SneakerModel sneakerModel, @NotNull InventoryModel inventoryModel, @NotNull ShippingAddress shippingAddress, @NotNull OrderStatus orderStatus, @NotNull Integer quantityBought) {
        this.orderIdentifier = orderIdentifier;
        this.customerModel = customerModel;
        this.sneakerModel = sneakerModel;
        this.inventoryModel = inventoryModel;
        this.shippingAddress = shippingAddress;
        this.orderStatus = orderStatus;
        this.quantityBought = quantityBought;
    }

     */
}

