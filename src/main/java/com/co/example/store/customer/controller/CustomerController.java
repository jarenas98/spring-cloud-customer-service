package com.co.example.store.customer.controller;

import com.co.example.store.customer.entity.Customer;
import com.co.example.store.customer.entity.Region;
import com.co.example.store.customer.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/customers")
@AllArgsConstructor
public class CustomerController {
    private ICustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<List<Customer>> findAllCustomers(@RequestParam(value = "regionID", required = false) Long regionId) {
        List<Customer> customers = new ArrayList<>();
        if (regionId == null) {
            customers = this.customerService.findAllCustomers();
            if (customers.isEmpty()) {

                return ResponseEntity.noContent().build();
            }
        } else {
            Region region = new Region();
            region.setId(regionId);
            customers = this.customerService.findCustomersByRegion(region);
            if (customers.isEmpty()) {
                log.error("Customers with region ID {} not found", regionId);

                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(customers);
    }

    @PostMapping("/")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer newCustomer,
                                                   BindingResult result) {
        log.info("Creating new Customer");
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Customer customer = this.customerService.createCustomer(newCustomer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer,
                                                   @PathVariable("id") Long customerId,
                                                   BindingResult result) {
        log.info("Updating a Customer with customer id [{}]", customerId);
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        customer.setId(customerId);
        Customer customerUpdated = this.customerService.updateCustomer(customer);
        if (customerUpdated == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customerUpdated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long customerId) {
        log.info("Deleting customer with id [{}]", customerId);

        Customer customerToDelete = Customer.builder().id(customerId).build();

        Customer customerDeleted= this.customerService.deleteCustomer(customerToDelete);
        if (customerDeleted == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customerDeleted);

    }

    public Customer getCustomer(Long customerId);
}
