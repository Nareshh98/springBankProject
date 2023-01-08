package com.bankProject;

import org.junit.jupiter.api.Order; 
  
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set; 
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.bankProject.entity.Account;
import com.bankProject.entity.Customer;
import com.bankProject.repository.CustomerRepository;

import jakarta.transaction.Transactional; 
  
 
  
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerAccountApplicationTest {

    @Autowired
    private CustomerRepository repository;

    /*@Autowired
    private AccountRepository repository1;*/ 
  
    @Test
    @Order(1)
    @Sql("/Many-To-Many.sql")
    void testCreateFirstCustomer() {
        Customer firstCustomer=new Customer();
        firstCustomer.setFirstName("Suresh");
        firstCustomer.setLastName("Kumar");
        firstCustomer.setEmail("suresh.kumar@wipro.com");

        repository.save(firstCustomer);
    }

    @Test
    @Order(2)
    void testCreateSecondCustomer() {
        Customer secondCustomer=new Customer();
        secondCustomer.setFirstName("Dan");
        secondCustomer.setLastName("Meyer");
        secondCustomer.setEmail("dan.meyer@wipro.com");

        Account account = new Account();
        account.setAccountType("Savings");
        account.setBalance(10907);

        Set<Account> accounts = new HashSet<Account>();
        accounts.add(account);
        secondCustomer.setAccounts(accounts);

        repository.save(secondCustomer);
    }

    @Test
    @Order(3)
    void testCreateThirdCustomer() {
        Customer thirdCustomer=new Customer();
        thirdCustomer.setFirstName("Tatha");
        thirdCustomer.setLastName("Sharma");
        thirdCustomer.setEmail("tatha.sharma@wipro.com");

        repository.save(thirdCustomer);
    }

    @Test
    @Order(4)
    @Transactional
    void getAllCustomers() {
        System.out.println("In get Customers method");

        Iterable<Customer> customers = repository.findAll();

        Iterator<Customer> itr=customers.iterator();

        while(itr.hasNext()) {
            Customer customer=itr.next();
            System.out.println("Customer Details:");
            System.out.println(customer);
            Set<Account> accounts = customer.getAccounts();
            System.out.println("Account Details:");
            for(Account account:accounts) {
                System.out.println(account);
            }
        }
    }

    @Test
    @Order(5)
    
    @Transactional
    void addNewAccountForCustomerOneAndTwo() {
        Optional<Customer> customers = repository.findById(Long.valueOf(1));
        Customer firstCustomer = customers.get();

        Account account = new Account();
        //account.setAccountNumber(2);
        account.setAccountType("Current");
        account.setBalance(1200);

        Set<Account> firstAccounts = firstCustomer.getAccounts();
        firstAccounts.add(account);
        //firstCustomer.setAccounts(firstAccounts);

        System.out.println("firstCustomer ::::::::: " + firstCustomer);

        //repository1.save(account);
        repository.save(firstCustomer);

        Optional<Customer> customers1 = repository.findById(Long.valueOf(2));
        Customer secondCustomer = customers1.get();

        Account account1 = new Account();
        //account1.setAccountNumber(3);
        account1.setAccountType("Current");
        account1.setBalance(6900);

        Set<Account> secondAccounts = secondCustomer.getAccounts();
        secondAccounts.add(account1);
        //secondCustomer.setAccounts(secondAccounts);

        System.out.println("secondCustomer ::::::::: " + secondCustomer);

        //repository1.save(account1);
        repository.save(secondCustomer);

        Optional<Customer> customers2 = repository.findById(Long.valueOf(3));
        Customer thirdCustomer = customers2.get();

        secondCustomer.getAccounts().forEach(accountToAdd -> {
            if(accountToAdd.getAccountNumber() == 3)
            {
                thirdCustomer.getAccounts().add(accountToAdd);
            }
        });

        System.out.println("In get Customers1 method");

        Iterable<Customer> customersX = repository.findAll();

        Iterator<Customer> itr=customersX.iterator();

        while(itr.hasNext()) {
            Customer customer=itr.next();
            System.out.println("Customer Details:");
            System.out.println(customer);
            Set<Account> accounts = customer.getAccounts();
            System.out.println("Account Details:");
            for(Account account2:accounts) {
                System.out.println(account2);
            }
        }
    }

    /*@Test
    @Order(6)
    @Transactional
    void getAllCustomers1() {
        System.out.println("In get Customers1 method");

        Iterable<Customer> customers = repository.findAll();

        Iterator<Customer> itr=customers.iterator();

        while(itr.hasNext()) {
            Customer customer=itr.next();
            System.out.println("Customer Details:");
            System.out.println(customer);
            Set<Account> accounts = customer.getAccounts();
            System.out.println("Account Details:");
            for(Account account:accounts) {
                System.out.println(account);
            }
        }
    }

    @Test
    @Order(7)
    @Transactional
    void getAllAccounts() {
        System.out.println("In getAllAccounts method");

        Iterable<Account> accounts = repository1.findAll();

        Iterator<Account> itr=accounts.iterator();

        while(itr.hasNext()) {
            Account account=itr.next();
            System.out.println("Account Details:");
            System.out.println(account);
            Set<Customer> customers = account.getCustomers();
            System.out.println("Customer Details:");
            for(Customer customer:customers) {
                System.out.println(customer);
            }
        }
    }*/ 
  
}


