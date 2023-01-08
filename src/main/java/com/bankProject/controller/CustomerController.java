package com.bankProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bankProject.entity.Account;
import com.bankProject.entity.Customer;
import com.bankProject.service.CustomerService;
 
 
@RestController
public class CustomerController {
    @Autowired
    CustomerService custService;

    @GetMapping("/helloSay")
    public String hello() {
        return "Say hello to Spring Boot Customer";
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return custService.getCustomers();
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        //return custService.createCustomer(customer);

        Customer createdCustomer = custService.createCustomer(customer);
        return new ResponseEntity<Customer>(createdCustomer, HttpStatus.CREATED);
    }

    @RequestMapping(value="/customers/{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable("id") int customerId) {
        Customer customer = custService.getCustomer(customerId);
        return customer;

    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable long id, @RequestBody Customer customer) throws Exception {
        Customer updatedCustomer = custService.updateCustomer(id, customer);
        return updatedCustomer;

    }

    @DeleteMapping("/customers/{id}")
    public Customer deleteCustomer(@PathVariable long id) throws Exception{
        Customer deletedCustomer = custService.deleteCustomer(id);
        return deletedCustomer;

    }

    @PostMapping("/customers/{id}/accounts")
    public ResponseEntity<Customer> createCustomerAccounts(@PathVariable long id, @RequestBody Account account) throws Exception {
        System.out.println("A Input Account --> " + account);
        Customer existingCustomer = custService.createCustomerAccounts(id, account);
        return new ResponseEntity<Customer>(existingCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/customers/{id}/accounts/add")
    public ResponseEntity<Customer> addCustomerAccounts(@PathVariable long id, @RequestBody Account account) throws Exception {
        System.out.println("A Input Account --> " + account);
        Customer existingCustomer = custService.addCustomerAccounts(id, account);
        return new ResponseEntity<Customer>(existingCustomer, HttpStatus.CREATED);
    }

}

