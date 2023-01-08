package com.bankProject.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set; 
  
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bankProject.entity.Account;
import com.bankProject.entity.Customer; 
  

  
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository; 
  
    @Test
    @Order(1)
    @Sql({ "/Many-To-Many.sql", "/NData.sql" })
    void testCreateCustomer() {
        System.out.println("Entering Create Customer");
        Customer firstCustomer = new Customer();
        firstCustomer.setFirstName("Dan");
        firstCustomer.setLastName("Meyer");
        firstCustomer.setEmail("dan.meyer@wipro.com"); 
  
        Account account = new Account();
        account.setAccountType("Savings");
        account.setBalance(10907); 
  
        Set<Account> accounts = new HashSet<Account>();
        accounts.add(account);
        firstCustomer.setAccounts(accounts); 
  
        Customer secondCustomer = new Customer();
        secondCustomer.setFirstName("Tatha");
        secondCustomer.setLastName("Sharma");
        secondCustomer.setEmail("tatha.sharma@wipro.com"); 
  
        Customer saveCustomer1 = repository.save(firstCustomer);
        Customer saveCustomer2 = repository.save(secondCustomer); 
  
        assertEquals(4, saveCustomer1.getCustomerId());
        assertEquals(5, saveCustomer2.getCustomerId()); 
  
        assertEquals("dan.meyer@wipro.com", saveCustomer1.getEmail());
        assertEquals("tatha.sharma@wipro.com", saveCustomer2.getEmail()); 
  
    } 
  
    @Test
    @Order(2)
    @Sql({ "/Many-To-Many.sql", "/NData.sql" })
    void testGetCustomer() {
        Optional<Customer> customers = repository.findById(Long.valueOf(2));
        Customer customer = customers.get(); 
  
        System.out.println("Test 2: Customer Details : " 
                + customer.getCustomerId() + "\t" + customer.getFirstName() 
                + "\t" + customer.getLastName() + "\t" + customer.getEmail()); 
  
        assertEquals("Shyam", customer.getFirstName()); 
  
    }

    @Test
    @Order(3)
    @Sql({ "/Many-To-Many.sql", "/NData.sql" })
    void testGetCustomers() {
        List<Customer> customers = (List<Customer>) repository.findAll(); 
  
        assertEquals(3, customers.size());
        assertEquals("Jodhu", customers.get(2).getFirstName());
        assertEquals("shyam.patel@wipro.com", customers.get(1).getEmail());
        assertEquals("Singh", customers.get(0).getLastName());
    } 
  
}
