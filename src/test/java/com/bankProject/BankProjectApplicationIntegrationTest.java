package com.bankProject;

import static org.junit.jupiter.api.Assertions.*; 

import java.util.HashSet;
import java.util.Set; 
  
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.bankProject.entity.Account;
import com.bankProject.entity.Customer;
import com.bankProject.entity.FundTransfer; 

  
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class CustomerAccountIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void testCreateCustomerOne() {
        Customer customer = new Customer(1,"Sam","Donald","sam.donald@wipro.com"); 
  
        Set<Account> customerAccounts = new HashSet<Account>();
        customerAccounts.add(new Account(1, "Savings", 69800));
        customerAccounts.add(new Account(2, "Current", 2000));
        customer.setAccounts(customerAccounts);

        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Customer> request=new HttpEntity<>(customer,headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity("/customers", request, String.class);

        assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    @Order(2)
    void testCreateCustomerTwo() {
        Customer customer = new Customer(2,"Kevin","Johnson","kevin.johnson@wipro.com"); 
  
        Set<Account> customerAccounts = new HashSet<Account>();
        customer.setAccounts(customerAccounts);

        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Customer> request=new HttpEntity<>(customer,headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity("/customers", request, String.class);

        assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    @Order(3)
    void testGetCustomer() {
        Customer customer = this.restTemplate.getForObject("/customers/1", Customer.class);

        System.out.println(customer);
    }

    @Test
    @Order(4)
    void testUpdateCustomer() {
        Customer customer = new Customer(2,"Kevin","Johnson","kevin.johnson1@wipro.com"); 
  
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Customer> request=new HttpEntity<>(customer,headers);
        this.restTemplate.put("/customers/2", request);
    }

    @Test
    @Order(5)
    void testGetCustomerTwo() {
        Customer customer = this.restTemplate.getForObject("/customers/2", Customer.class);

        System.out.println(customer);
    }

    @Test
    @Order(6)
    void testCreateCustomerAccounts() {
        Account account = new Account();
        account.setAccountType("Savings");
        account.setBalance(7800); 
  
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Account> request=new HttpEntity<>(account,headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity("/customers/2/accounts", request, String.class);

        assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    @Order(7)
    void testGetCustomerThree() {
        Customer customer = this.restTemplate.getForObject("/customers/2", Customer.class);

        System.out.println(customer);
    }

    @Test
    @Order(8)
    void testFundTransfer() {

        Customer customer = new Customer(3,"Roger","Dev","roger.dev@wipro.com");

        Set<Account> customerAccounts = new HashSet<Account>();
        Account account1 = new Account(4, "Savings", 69800);
        Account account2 = new Account(5, "Savings", 2000);
        customerAccounts.add(account1);
        customerAccounts.add(account2);
        customer.setAccounts(customerAccounts);

        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Customer> request=new HttpEntity<>(customer,headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity("/customers", request, String.class);

        FundTransfer fundTransfer = new FundTransfer();
        fundTransfer.setFromAccount(account1);
        fundTransfer.setToAccount(account2);
        fundTransfer.setAmount(200);

        HttpHeaders headers1 = new org.springframework.http.HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<FundTransfer> request1= new HttpEntity<>(fundTransfer,headers1);
        this.restTemplate.put("/accounts/transferFund", request1);

    }

    @Test
    @Order(9)
    void testGetCustomerFour() {
        Customer customer = this.restTemplate.getForObject("/customers/3", Customer.class);

        System.out.println(customer);
    } 
  
}
