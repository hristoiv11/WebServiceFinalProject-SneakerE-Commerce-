package com.laboneproject.purchases.dataaccesslayer;



import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface OrderRepository extends MongoRepository<Order,String> {

    Order findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(String customerId,String orderId);

    List<Order> findOrdersByCustomerModel_CustomerId(String customerId);

    //Order findOrderByOrderIdentifier_OrderId(String orderId);
    //Order findOrderByCustomerIdentifier_CustomerIdAndOrderIdentifier_OrderId(String customerId,String orderId);
    //List<Order> findOrdersByCustomerIdentifier_CustomerId(String customerId);
}

