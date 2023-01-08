package com.bankProject.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankProject.entity.Account;
import com.bankProject.entity.Customer;
import com.bankProject.exception.NotFoundException;
import com.bankProject.repository.CustomerRepository;
 

 
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public List<Customer> getCustomers() {
        return (List<Customer>) repository.findAll();
    }
 
    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }
 
    public Customer getCustomer(long customerId) {
        Optional<Customer> customer = repository.findById(customerId);
        if(customer.isEmpty()) {
            throw new NotFoundException("Customer with id-->"+customerId+" not found.");
        }
        return customer.get();
    }
 
    public Customer updateCustomer(long customerId, Customer customer) throws Exception {
        Optional<Customer> customers = repository.findById(customerId);
        if(customers.isEmpty()) {
            throw new NotFoundException("Customer with id-->"+customerId+" not found.");
        }
        Customer updateCustomer = customers.get();

        if(updateCustomer==null) {
            throw new NotFoundException("Customer with id-->"+ customerId +" not found.");
        }
        updateCustomer.setFirstName(customer.getFirstName());
        updateCustomer.setLastName(customer.getLastName());
        updateCustomer.setEmail(customer.getEmail());
        Customer updatedCustomer = repository.save(updateCustomer);
        return updatedCustomer;
    }
 
    public Customer deleteCustomer(long customerId) throws Exception {
        Optional<Customer> customers = repository.findById(customerId);
        if(customers.isEmpty()) {
            throw new NotFoundException("Customer with id-->"+customerId+" not found.");
        }
        Customer deleteCustomer = customers.get();

        if(deleteCustomer==null) {
            throw new NotFoundException("Customer with id-->"+ customerId +" not found.");
        } 

        repository.deleteById(customerId);
        return deleteCustomer;
    }

    public Customer createCustomerAccounts(long customerId, Account account) throws Exception {
        Optional<Customer> customers = repository.findById(customerId);
        if(customers.isEmpty()) {
            throw new NotFoundException("Customer with id-->"+customerId+" not found.");
        }
        Customer existingCustomer = customers.get();

        if(existingCustomer == null) {
            throw new NotFoundException("Customer with id-->"+ customerId +" not found.");
        }

        Set<Account> customerAccounts = existingCustomer.getAccounts();

        customerAccounts.add(account);

        return repository.save(existingCustomer);
    }

    public Customer addCustomerAccounts(long customerId, Account account) throws Exception {
        Optional<Customer> customers = repository.findById(customerId);
        if(customers.isEmpty()) {
            throw new NotFoundException("Customer with id-->"+customerId+" not found.");
        }
        Customer existingCustomer = customers.get();

        if(existingCustomer == null) {
            throw new NotFoundException("Customer with id-->"+ customerId +" not found.");
        }

        if(account.getAccountNumber() == 0) {
            throw new Exception("Invalid account id");
        }

        Set<Account> customerAccounts = existingCustomer.getAccounts();
        customerAccounts.add(account);

        return repository.save(existingCustomer);
    }
}

