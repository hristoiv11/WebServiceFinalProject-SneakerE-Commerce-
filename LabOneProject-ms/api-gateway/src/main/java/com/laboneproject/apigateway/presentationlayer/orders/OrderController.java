package com.laboneproject.apigateway.presentationlayer.orders;


import com.laboneproject.apigateway.businesslayer.orders.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/customers/{customerId}/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService)   {
        this.orderService = orderService;
    }

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@PathVariable String customerId) {
        return ResponseEntity.ok().body(orderService.getAllOrders(customerId));
    }

    @GetMapping(

            value ="/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderResponseDTO> getOrderByOrderId(@PathVariable String orderId,@PathVariable String customerId) {
        OrderResponseDTO orderResponseDTO = orderService.getOrderByOrderId(orderId,customerId);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTO);
    }


    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO orderRequestDTO,@PathVariable String customerId) {
        OrderResponseDTO addedOrder = orderService.addOrder(orderRequestDTO,customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedOrder);
    }

    @PutMapping(

            value = "/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @PathVariable String orderId,@PathVariable String customerId) {
        OrderResponseDTO updatedOrder = orderService.updateOrder(orderRequestDTO, orderId,customerId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    @DeleteMapping(

            value = "/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId,@PathVariable String customerId) {
        orderService.deleteOrder(orderId,customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
