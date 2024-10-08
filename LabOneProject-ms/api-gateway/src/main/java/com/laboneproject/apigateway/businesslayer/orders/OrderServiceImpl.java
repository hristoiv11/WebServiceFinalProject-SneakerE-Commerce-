package com.laboneproject.apigateway.businesslayer.orders;

import com.laboneproject.apigateway.dataaccesslayer.customers.CustomersServiceClient;
import com.laboneproject.apigateway.dataaccesslayer.orders.OrderServiceClient;
import com.laboneproject.apigateway.dataaccesslayer.orders.OrderStatus;
import com.laboneproject.apigateway.datamapperlayer.customers.CustomerResponseMapper;
import com.laboneproject.apigateway.datamapperlayer.orders.OrderResponseMapper;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerRequestDTO;
import com.laboneproject.apigateway.presentationlayer.customers.CustomerResponseDTO;
import com.laboneproject.apigateway.presentationlayer.orders.OrderRequestDTO;
import com.laboneproject.apigateway.presentationlayer.orders.OrderResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderServiceClient orderServiceClient;
    private final OrderResponseMapper orderResponseMapper;

    public OrderServiceImpl(OrderServiceClient orderServiceClient, OrderResponseMapper orderResponseMapper) {
        this.orderServiceClient = orderServiceClient;
        this.orderResponseMapper = orderResponseMapper;
    }

    @Override
    public List<OrderResponseDTO> getAllOrders(String customerId) {
        return orderResponseMapper.responseModelListToReposeList(orderServiceClient.getAllOrders(customerId));
    }

    @Override
    public OrderResponseDTO getOrderByOrderId(String orderId,String customerId) {
        return orderResponseMapper.responseModelToResponseModel(orderServiceClient.getOrderByOrderId(orderId,customerId));
    }

    @Override
    public OrderResponseDTO addOrder(OrderRequestDTO orderRequestDTO,String customerId) {

        orderRequestDTO.setOrderStatus(OrderStatus.COMPLETED);
        return orderResponseMapper.responseModelToResponseModel(orderServiceClient.addOrder(orderRequestDTO,customerId));
    }

    @Override
    public OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, String orderId,String customerId) {
        orderRequestDTO.setOrderStatus(OrderStatus.COMPLETED);
        return orderResponseMapper.responseModelToResponseModel(orderServiceClient.updateOrder(orderId,orderRequestDTO,customerId));
    }

    @Override
    public void deleteOrder(String orderId,String customerId) {
        orderServiceClient.deleteOrder(orderId,customerId);
    }

}
