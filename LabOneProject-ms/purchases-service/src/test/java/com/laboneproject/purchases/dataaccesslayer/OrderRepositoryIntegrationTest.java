package com.laboneproject.purchases.dataaccesslayer;

import com.laboneproject.purchases.domainclientlayer.customers.CustomerModel;
import com.laboneproject.purchases.domainclientlayer.customers.CustomerStatus;
import com.laboneproject.purchases.domainclientlayer.sneakers.SneakerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
@ActiveProfiles("test")
class OrderRepositoryIntegrationTest {

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setup() {
        // Clean up the database before each test
        orderRepository.deleteAll();
    }

    @Test
    public void whenSaveOrder_thenFindByIdReturnsOrder() {
        // Arrange
        Order order = createSampleOrder();

        // Act
        Order savedOrder = orderRepository.save(order);
        Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);

        // Assert
        assertNotNull(foundOrder);
        assertEquals(savedOrder.getId(), foundOrder.getId());
        // Add more assertions as needed
    }

    @Test
    public void whenNoOrdersExist_thenFindAllReturnsEmptyList() {
        // Act
        List<Order> orders = orderRepository.findAll();

        // Assert
        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }

    @Test
    public void whenSaveMultipleOrders_thenFindAllReturnsAllOrders() {
        // Arrange
        Order order1 = createSampleOrder();
        Order order2 = createSampleOrder();
        orderRepository.save(order1);
        orderRepository.save(order2);

        // Act
        List<Order> orders = orderRepository.findAll();

        // Assert
        assertNotNull(orders);
        assertEquals(2, orders.size());
        // Add more assertions as needed
    }

    @Test
    public void whenFindByCustomerId_thenReturnsOrdersForCustomer() {
        // Arrange
        String customerId = "001";
        Order order1 = createSampleOrder();
        Order order2 = createSampleOrder();
        order1.getCustomerModel().setCustomerId(customerId);
        order2.getCustomerModel().setCustomerId("002"); // Different customer
        orderRepository.save(order1);
        orderRepository.save(order2);

        // Act
        List<Order> orders = orderRepository.findOrdersByCustomerModel_CustomerId(customerId);

        // Assert
        assertNotNull(orders);
        assertEquals(1, orders.size());
        // Add more assertions as needed
    }

    private Order createSampleOrder() {
        CustomerModel customer = CustomerModel.builder()
                .customerId("001")
                .customerFName("John")
                .customerLName("Doe")
                .number("313-321-312")
                .email("email@gmail.com")
                .customerStatus(CustomerStatus.AVAILABLE)
                .build();

        SneakerModel sneaker = SneakerModel.builder()
                .sneakerId("040")
                .model("Nike 270")
                .price("100")
                .size(8)
                .color("white and blue")
                .releaseYear("2019")
                .availableStore("Nike")
                .description("build with high quality materials")
                .type("sneaker")
                .build();

        ShippingAddress address = ShippingAddress.builder()
                .street("1180 RUE DOLLARD")
                .city("Longueil")
                .state("QC")
                .country("CANADA")
                .postalCode("J4K 4M7")
                .build();

        OrderIdentifier orderIdentifier = new OrderIdentifier();

        return Order.builder()
                .orderIdentifier(orderIdentifier)
                .customerModel(customer)
                .sneakerModel(sneaker)
                .orderStatus(OrderStatus.PAID)
                .shippingAddress(address)
                .build();
    }

    /*

    @Test
    public void whenOrderIdIsValid_thenReturnOrders(){

        //arrange
        var orderIdentifer = new OrderIdentifier();

        var customerModel = CustomerModel.builder()
                .customerId("001")
                .customerFName("John")
                .customerLName("Doe")
                .number("313-321-312")
                .email("email@gmail.com")
                .build();

        var sneakerModel = SneakerModel.builder()
                .sneakerId("040")
                .model("Nike 270")
                .price("100")
                .size(8)
                .color("white and blue")
                .releaseYear("2019")
                .availableStore("Nike")
                .description("build with high quality materials")
                .type("sneaker")
                .build();

        var address = ShippingAddress.builder()
                .street("1180 RUE DOLLARD")
                .city("Longueil")
                .state("QC")
                .country("CANADA")
                .postalCode("J4K 4M7")
                .build();


        var order1 = Order.builder()
                .orderIdentifier(orderIdentifer)
                .customerModel(customerModel)
                .sneakerModel(sneakerModel)
                .orderStatus(OrderStatus.PAID)
                .shippingAddress(address)
                .build();


        var order2 = Order.builder()
                .orderIdentifier(new OrderIdentifier())
                .customerModel(customerModel)
                .sneakerModel(sneakerModel)
                .orderStatus(OrderStatus.PAID)
                .shippingAddress(address)
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);

        //act
        List<Order> orders = orderRepository.findOrdersByCustomerModel_CustomerId(customerModel.getCustomerId());

        //assert

        assertNotNull(orders);
        assertEquals(2,orders.size());

    }

    @Test
    public void whenCustomerIdIsInvalid_thenNoOrdersReturned() {
        // Arrange
        var nonExistentCustomerId = "999";  // Assuming this customer ID does not exist in the database

        // Act
        List<Order> orders = orderRepository.findOrdersByCustomerModel_CustomerId(nonExistentCustomerId);

        // Assert
        assertNotNull(orders); // Ensure the result is not null
        assertTrue(orders.isEmpty()); // Check that the list is empty
    }

    */
}


