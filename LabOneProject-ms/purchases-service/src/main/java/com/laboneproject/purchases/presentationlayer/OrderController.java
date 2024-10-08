package com.laboneproject.purchases.presentationlayer;



import com.laboneproject.purchases.businesslayer.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/customers/{customerId}/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService= orderService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersForCustomers(@PathVariable String customerId) {

        return ResponseEntity.ok().body(orderService.getAllOrdersForCustomers(customerId));
    }

    @GetMapping(value = "/{orderId}",produces = "application/json")
    public ResponseEntity<OrderResponseDTO> getCustomerOrderById(@PathVariable String orderId,@PathVariable String customerId) {
        /*
        OrderResponseDTO orderResponseDTO = orderService.getCustomerOrderById(orderId,customerId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTO);
         */

        return ResponseEntity.ok().body(orderService.getCustomerOrderById(customerId,orderId));
    }

    @PostMapping(produces = "application/json",consumes = "application/json")
    public ResponseEntity<OrderResponseDTO> addCustomerOrder(@PathVariable String customerId,@RequestBody OrderRequestDTO orderRequestDTO) {

        /*
        OrderResponseDTO addedOrder = orderService.addCustomerOrder(customerId,orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedOrder);

         */

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addCustomerOrder(customerId,orderRequestDTO));
    }

    @PutMapping(value = "/{orderId}",produces = "application/json")
    public ResponseEntity<OrderResponseDTO> updateCustomerOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @PathVariable String orderId,
            @PathVariable String customerId) {

        /*
        OrderResponseDTO updatedOrder = orderService.updateCustomerOrder(orderId,orderRequestDTO,customerId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
         */

        return ResponseEntity.ok().body(orderService.updateCustomerOrder(orderId,orderRequestDTO,customerId));

    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<Void> deleteCustomerOrder(@PathVariable String orderId,@PathVariable String customerId) {
        orderService.deleteCustomerOrder(orderId,customerId);
        //return ResponseEntity.ok().build();
        return ResponseEntity.noContent().build();
    }

}

