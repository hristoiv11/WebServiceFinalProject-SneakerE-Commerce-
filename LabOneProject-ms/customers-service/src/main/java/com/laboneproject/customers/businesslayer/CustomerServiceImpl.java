package com.laboneproject.customers.businesslayer;


import com.laboneproject.customers.dataaccesslayer.Customer;
import com.laboneproject.customers.dataaccesslayer.CustomerIdentifier;
import com.laboneproject.customers.dataaccesslayer.CustomerRepository;
import com.laboneproject.customers.dataaccesslayer.Wishlist;
import com.laboneproject.customers.datamapperlayer.CustomerRequestMapper;
import com.laboneproject.customers.datamapperlayer.CustomerResponseMapper;
import com.laboneproject.customers.presentationlayer.CustomerRequestDTO;
import com.laboneproject.customers.presentationlayer.CustomerResponseDTO;

import com.laboneproject.customers.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerResponseMapper customerResponseMapper;
    private final CustomerRequestMapper customerRequestMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerResponseMapper customerResponseMapper, CustomerRequestMapper customerRequestMapper) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
        this.customerRequestMapper = customerRequestMapper;
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {

        List<Customer> customers = customerRepository.findAll();
        return customerResponseMapper.entityListToResponseDTOList(customers);
    }

    @Override
    public CustomerResponseDTO getCustomerById(String customerId) {

        Customer customer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);
        if (customer == null) {
            throw new NotFoundException("Unknown customerId: " + customerId);
        }
        return customerResponseMapper.entityToResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO) {

        Wishlist wishlist = new Wishlist(customerRequestDTO.getCustomerPreferredSneaker(),customerRequestDTO.getCustomerPreferredBrand()
        ,customerRequestDTO.getCustomerPreferredSize());
        Customer customer = customerRequestMapper.requestDTOToEntity(customerRequestDTO, new CustomerIdentifier(),wishlist);
        return customerResponseMapper.entityToResponseDTO(customerRepository.save(customer));
    }

    @Override
    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customerRequestDTO, String customerId) {

        Customer foundCustomer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);

        /*
        if (foundCustomer == null) {
            throw new NotFoundException("Unknown customerId: " + customerId);
        }

         */

        Wishlist wishlist = new Wishlist(customerRequestDTO.getCustomerPreferredSneaker(),customerRequestDTO.getCustomerPreferredBrand(),customerRequestDTO.getCustomerPreferredSize());
        Customer updatedCustomer= customerRequestMapper.requestDTOToEntity(customerRequestDTO, foundCustomer.getCustomerIdentifier(),wishlist);
        updatedCustomer.setId(foundCustomer.getId());

        return customerResponseMapper.entityToResponseDTO(customerRepository.save(updatedCustomer));
    }

    @Override
    public void deleteCustomer(String customerId) {

        Customer customer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);

        /*
        if (customer == null) {
            throw new NotFoundException("Unknown customerId: " + customerId);
        }

         */
        customerRepository.delete(customer);
    }
}
