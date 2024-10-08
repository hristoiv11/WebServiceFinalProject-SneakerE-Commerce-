package com.laboneproject.customers.dataaccesslayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryIntegrationTest {

    private final String INVALID_CUSTOMER_ID = "045";


    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    public void setupDb(){
        customerRepository.deleteAll();
    }


    @Test
    public void whenCustomerExists_thenReturnCorrectCustomerDetails(){
        //arrange
        Customer customer= new Customer("Ico","Picha","513-321-213","email@gmail.com"
                ,new Wishlist("adidas gazelle","adidas","10"),CustomerStatus.BANNED);

        customerRepository.save(customer);
        //act
        Customer customer1= customerRepository.findCustomerByCustomerIdentifier_CustomerId(customer.getCustomerIdentifier().getCustomerId());

        //assert
        assertNotNull(customer);
        assertEquals(customer.getCustomerIdentifier().getCustomerId(), customer1.getCustomerIdentifier().getCustomerId());
        assertEquals(customer.getCustomerFName(),customer1.getCustomerFName());
        assertEquals(customer.getCustomerLName(),customer1.getCustomerLName());
        assertEquals(customer.getNumber(),customer1.getNumber());
        assertEquals(customer.getEmail(),customer1.getEmail());
        assertEquals(customer.getWishlist().getCustomerPreferredSneaker(),customer1.getWishlist().getCustomerPreferredSneaker());
        assertEquals(customer.getWishlist().getCustomerPreferredBrand(),customer1.getWishlist().getCustomerPreferredBrand());
        assertEquals(customer.getWishlist().getCustomerPreferredSize(),customer1.getWishlist().getCustomerPreferredSize());
        assertEquals(customer.getCustomerStatus(),customer1.getCustomerStatus());

    }

    @Test
    public void whenCustomerDoesNotExists_thenReturnNull(){

        Customer customer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(INVALID_CUSTOMER_ID);

        assertNull(customer,"Invalid customerId");
    }

    @Test
    public void whenFindAll_thenReturnAllCustomers() {
        // Arrange
        Customer customer1 = new Customer("Georgi","Ivanov","513-321-213","email@gmail.com"
                ,new Wishlist("nike tiempo","nike","10"),CustomerStatus.AVAILABLE);
        Customer customer2 = new Customer("Ivailo","Ivanov","513-321-213","email@gmail.com"
                ,new Wishlist("new balance 2002r","new balance","8"),CustomerStatus.RESTRICTED);
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // Act
        List<Customer> allCustomers = customerRepository.findAll();

        // Assert
        assertEquals(2, allCustomers.size());
        assertTrue(allCustomers.contains(customer1));
        assertTrue(allCustomers.contains(customer2));
    }

}