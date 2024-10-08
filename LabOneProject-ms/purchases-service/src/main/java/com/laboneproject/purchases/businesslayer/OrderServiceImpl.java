package com.laboneproject.purchases.businesslayer;



import com.laboneproject.purchases.dataaccesslayer.Order;
import com.laboneproject.purchases.dataaccesslayer.OrderIdentifier;
import com.laboneproject.purchases.dataaccesslayer.OrderRepository;
import com.laboneproject.purchases.dataaccesslayer.OrderStatus;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerModel;
import com.laboneproject.purchases.domainclientlayer.customers.CustomersServiceClient;
import com.laboneproject.purchases.domainclientlayer.inventories.InventoryModel;
import com.laboneproject.purchases.domainclientlayer.inventories.InventoryServiceClient;
import com.laboneproject.purchases.domainclientlayer.sneakers.SneakerModel;
import com.laboneproject.purchases.domainclientlayer.sneakers.SneakerServiceClient;
import com.laboneproject.purchases.mapperlayer.OrderRequestMapper;
import com.laboneproject.purchases.mapperlayer.OrderResponseMapper;
import com.laboneproject.purchases.presentationlayer.OrderRequestDTO;
import com.laboneproject.purchases.presentationlayer.OrderResponseDTO;

import com.laboneproject.purchases.utils.exceptions.InvalidInputException;
import com.laboneproject.purchases.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderResponseMapper orderResponseMapper;
    private final OrderRequestMapper orderRequestMapper;
    private final CustomersServiceClient customersServiceClient;
    private final InventoryServiceClient inventoryServiceClient;
    private final SneakerServiceClient sneakerServiceClient;


    public OrderServiceImpl(OrderRepository orderRepository, OrderResponseMapper orderResponseMapper, OrderRequestMapper orderRequestMapper, CustomersServiceClient customersServiceClient, InventoryServiceClient inventoryServiceClient, SneakerServiceClient sneakerServiceClient) {
        this.orderRepository = orderRepository;
        this.orderResponseMapper = orderResponseMapper;
        this.orderRequestMapper = orderRequestMapper;
        this.customersServiceClient = customersServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
        this.sneakerServiceClient = sneakerServiceClient;
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersForCustomers(String customerId) {

        CustomerModel foundCustomer = customersServiceClient.getCustomerByCustomerId(customerId);
        if(foundCustomer ==null){
            throw new InvalidInputException("CustomerId provided is Invalid " + customerId);
        }
        //List<OrderResponseDTO> orderResponseDTO = new ArrayList<>();

        List<Order> order = orderRepository.findOrdersByCustomerModel_CustomerId(customerId);

        return orderResponseMapper.entityListToResponseModelList(order);
    }

    @Override
    public OrderResponseDTO getCustomerOrderById(String customerId,String orderId) {

        CustomerModel foundCustomer= customersServiceClient.getCustomerByCustomerId(customerId);
        if(foundCustomer ==null){
            throw new InvalidInputException("Unknown customerId:" + customerId);
        }

        Order foundOrder = orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId,orderId);
        if(foundOrder ==null){
            throw new NotFoundException("Unknown OrderId provided " + orderId);
        }


        foundOrder.setOrderStatus(OrderStatus.PAID);
        return orderResponseMapper.entityToResponseDTO(foundOrder);
    }


    @Override
    public OrderResponseDTO addCustomerOrder(String customerId, OrderRequestDTO orderRequestDTO) {

        CustomerModel foundCustomer = customersServiceClient.getCustomerByCustomerId(customerId);
        if (foundCustomer == null){
            throw new InvalidInputException("CustomerId provided is Invalid" + customerId);
        }

        //verify sneaker exists
        SneakerModel foundSneaker = sneakerServiceClient.getSneakerBySneakerId(
                orderRequestDTO.getSneakerId());
        if (foundSneaker == null){
            throw new InvalidInputException("SneakerId provided is Invalid" + orderRequestDTO.getSneakerId());
        }
        //verify the inventory exists
        InventoryModel foundInventory = inventoryServiceClient.getInventoryByInventoryId(
                orderRequestDTO.getInventoryId());
        if(foundInventory == null){
            throw new InvalidInputException("InventoryId provided is Invalid" + orderRequestDTO.getInventoryId());
        }

        orderRequestDTO.setOrderStatus(OrderStatus.COMPLETED);
        Order order = orderRequestMapper.requestDTOToEntity(orderRequestDTO, new OrderIdentifier(),
                foundInventory,foundCustomer,foundSneaker);


        Order savedOrder = orderRepository.save(order);
        return orderResponseMapper.entityToResponseDTO(savedOrder);

    }


    @Override
    public OrderResponseDTO updateCustomerOrder(String orderId,OrderRequestDTO orderRequestDTO, String customerId) {

        CustomerModel foundCustomer= customersServiceClient.getCustomerByCustomerId(customerId);
        if(foundCustomer== null){
            throw new InvalidInputException("customerId provided is invalid: " + customerId);
        }

        Order foundOrder = orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId);
        if (foundOrder == null) {
            throw new NotFoundException("Unknown OrderId provided " + orderId);
        }
        //verify employee exist
        SneakerModel foundSneaker = sneakerServiceClient.getSneakerBySneakerId(orderRequestDTO.getSneakerId());
        if (foundSneaker == null) {
            throw new InvalidInputException("SneakerId provided is Invalid " + orderRequestDTO.getSneakerId());
        }

        //verify inventory exist
        InventoryModel foundInventory = inventoryServiceClient.getInventoryByInventoryId(orderRequestDTO.getInventoryId());
        if (foundInventory == null) {
            throw new InvalidInputException("InventoryId provided is Invalid " + orderRequestDTO.getInventoryId());
        }

        Order orders= orderRequestMapper.requestDTOToEntity(orderRequestDTO,foundOrder.getOrderIdentifier(),
                foundInventory,foundCustomer,foundSneaker);

        orders.setId(foundOrder.getId());
        Order savedOrder= orderRepository.save(orders);

        return orderResponseMapper.entityToResponseDTO(savedOrder);
    }

    @Override
    public void deleteCustomerOrder(String orderId,String customerId) {

        CustomerModel foundCustomer =customersServiceClient.getCustomerByCustomerId(customerId);
        if (foundCustomer == null){
            throw new InvalidInputException("CustomerId provided is Invalid" + customerId);
        }

        Order order= orderRepository.findOrderByCustomerModel_CustomerIdAndOrderIdentifier_OrderId(customerId,orderId);

        if(order == null){

            throw new NotFoundException("Unknown OrderId provided:" + orderId);
        }

        orderRepository.delete(order);
    }

}







