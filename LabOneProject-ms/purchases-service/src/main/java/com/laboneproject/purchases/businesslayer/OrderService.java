package com.laboneproject.purchases.businesslayer;




import com.laboneproject.purchases.presentationlayer.OrderRequestDTO;
import com.laboneproject.purchases.presentationlayer.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> getAllOrdersForCustomers(String customerId);
    OrderResponseDTO getCustomerOrderById(String customerId,String orderId);
    OrderResponseDTO addCustomerOrder(String customerId, OrderRequestDTO orderRequestDTO);
    OrderResponseDTO updateCustomerOrder(String orderId,OrderRequestDTO orderRequestDTO, String customerId);
    void deleteCustomerOrder(String orderId,String customerId);
}




