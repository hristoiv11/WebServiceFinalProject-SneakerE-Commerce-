package com.laboneproject.purchases.utils;

import com.laboneproject.purchases.dataaccesslayer.*;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerModel;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerStatus;
import com.laboneproject.purchases.domainclientlayer.inventories.InventoryModel;
import com.laboneproject.purchases.domainclientlayer.sneakers.SneakerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class DatabaseLoaderService implements CommandLineRunner {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception{

        var orderIdentifier = new OrderIdentifier();
        var inventoryModel = InventoryModel.builder()
                .inventoryId("011")
                //.availableLevel("3")//here was only stock level , I tried with availableLevel and restockLevel only
                //.restockLevel("10")
                //.stockLevel(new StockLevel("3","10"))
                .build();

        var customerModel = CustomerModel.builder()
                .customerId("001")
                .customerFName("John")
                .customerLName("Doe")
                .number("313-321-312")
                .email("email@gmail.com")
                //.customerPreferredSneaker("nike air max plus")
                //.customerPreferredBrand("nike")
                //.customerPreferredSize("11")
                //.wishlist(new Wishlist("nike air max plus","nike","11"))
                .customerStatus(CustomerStatus.AVAILABLE)
                .build();

        var sneakerModel = SneakerModel.builder()
                .sneakerId("040")
                .model("Nike 270")
                .price("100")
                .size(8)
                .color("white and blue")
                //.releaseYear("2019")
                //.availableStore("Nike")
                //.description("build with high quality materials")
                //.type("sneaker")
                .build();


        var shippingAddress = ShippingAddress.builder()
                .street("1180 RUE DOLLARD")
                .city("Longueil")
                .state("QC")
                .country("CANADA")
                .postalCode("J4K 4M7")
                .build();



        var order1 = Order.builder()
                .orderIdentifier(orderIdentifier)
                .customerModel(customerModel)
                .sneakerModel(sneakerModel)
                .inventoryModel(inventoryModel)
                .quantityBought(2)
                .shippingAddress(shippingAddress)
                .orderStatus(OrderStatus.PAID)
                .build();

        orderRepository.save(order1);

    }
}
