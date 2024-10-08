package com.laboneproject.apigateway.businesslayer.orders;

import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.orders.OrderRequestDTO;
import com.laboneproject.apigateway.presentationlayer.orders.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> getAllOrders(String customerId);
    OrderResponseDTO getOrderByOrderId(String orderId,String customerId);
    OrderResponseDTO addOrder(OrderRequestDTO orderRequestDTO,String customerId);
    OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, String customerId,String orderId);
    void deleteOrder(String orderId,String customerId);
}
