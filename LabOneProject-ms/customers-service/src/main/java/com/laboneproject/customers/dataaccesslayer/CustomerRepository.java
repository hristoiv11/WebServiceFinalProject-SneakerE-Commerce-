package com.laboneproject.customers.dataaccesslayer;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    //Optional<Customer> findByNumber(String number);
    Customer findCustomerByCustomerIdentifier_CustomerId(String customerId);
}
