package com.co.example.store.customer.service;

import com.co.example.store.customer.entity.Customer;
import com.co.example.store.customer.entity.Region;
import com.co.example.store.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService{

    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return null;
    }

    @Override
    public Customer createCustomer(Customer newCustomer) {
        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer getCustomer(Long customerId) {
        return null;
    }
}
