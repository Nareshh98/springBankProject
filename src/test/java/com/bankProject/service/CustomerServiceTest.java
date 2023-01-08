package com.bankProject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when; 
  
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set; 
  
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bankProject.entity.Account;
import com.bankProject.entity.Customer;
import com.bankProject.exception.NotFoundException;
import com.bankProject.repository.CustomerRepository; 
  
 
  
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    CustomerService service;
    @Mock
    private CustomerRepository repository; 
  
    @Test
    @Order(1)
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Rishab");
        customer.setLastName("Ganguly");
        customer.setEmail("rishab.ganguly@wipro.com");

        Customer mockCustomer = new Customer();
        mockCustomer.setCustomerId(1);
        mockCustomer.setFirstName("Pritam");
        mockCustomer.setLastName("Ganguly");
        mockCustomer.setEmail("pritam.ganguly@wipro.com");

        when(repository.save(any(Customer.class))).thenReturn(mockCustomer);
        Customer cust = service.createCustomer(customer);
        System.out.println(cust);
        verify(repository,atLeastOnce()).save(customer);
        assertEquals(1, cust.getCustomerId());
    }

    @Test
    @Order(2)
    void testCreateCustomer2() {
        Customer customer = new Customer();
        customer.setFirstName("Purba");
        customer.setLastName("Ganguly");
        customer.setEmail("purba.ganguly@wipro.com");

        Account account = new Account();
        account.setAccountType("Savings");
        account.setBalance(34000);

        Set<Account> customerAccounts = new HashSet<Account>();
        customerAccounts.add(account);

        customer.setAccounts(customerAccounts);

        Customer mockCustomer = new Customer();
        mockCustomer.setCustomerId(1);
        mockCustomer.setFirstName("Pritam");
        mockCustomer.setLastName("Ganguly");
        mockCustomer.setEmail("pritam.ganguly@wipro.com");

        Account mockAccount = new Account();
        mockAccount.setAccountNumber(1);
        mockAccount.setAccountType("Savings");
        mockAccount.setBalance(7000);

        Set<Account> mockCustomerAccounts = new HashSet<Account>();
        mockCustomerAccounts.add(mockAccount);

        mockCustomer.setAccounts(mockCustomerAccounts);

        when(repository.save(any(Customer.class))).thenReturn(mockCustomer);
        Customer cust = service.createCustomer(customer);
        System.out.println(cust);
        verify(repository,atLeastOnce()).save(customer);
        assertEquals(1, cust.getCustomerId());
    }

    @Test
    @Order(3)
    void testGetAllCustomer() {
        List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(new Customer(1,"Vinay","kgkg","dfgd.kgkg@wipro.com"));
        customerList.add(new Customer(2,"Dgfh","Dgghh","dfgd.tyuuy@wipro.com"));

        Account account = new Account();
        account.setAccountNumber(1);
        account.setAccountType("Savings");
        account.setBalance(34000);

        Set<Account> customerAccounts = new HashSet<Account>();
        customerAccounts.add(account);

        customerList.get(1).setAccounts(customerAccounts);

        when(repository.findAll()).thenReturn(customerList);

        List<Customer> customers = service.getCustomers();

        for(Customer customer: customers) {
            System.out.println(customer.getCustomerId() + "\t" + customer.getFirstName());
            Set<Account> tempAccounts = customer.getAccounts();
            if(tempAccounts != null) {
                for(Account tempAccount: tempAccounts) {
                    System.out.println(tempAccount.getAccountNumber()+ "\t" + tempAccount.getAccountType());
                }
            }
        }
        assertEquals(2,customers.size());
        assertEquals("Dgfh",customers.get(1).getFirstName());
    }

    @Test
    @Order(4)
    void testGetCustomer() {
        Optional<Customer> customers = Optional.of(new Customer(3,"Tim","Paine","tim.paine@wipro.com"));
        when(repository.findById(any(Long.class))).thenReturn(customers);
        Customer customer = service.getCustomer(3);

        System.out.println(customer.getCustomerId() + "\t" + customer.getFirstName());
        assertEquals("Paine", customer.getLastName());
    }

@Test
    @Order(5)
    void testUpdateCustomer() throws Exception {
        Optional<Customer> customers = Optional.of(new Customer(3,"Tim","Paine","tim.paine@wipro.com"));
        when(repository.findById(any(Long.class))).thenReturn(customers);

        Customer customer = customers.get();

        Customer mockCustomer = new Customer();
        mockCustomer.setCustomerId(1);
        mockCustomer.setFirstName("Pritam");
        mockCustomer.setLastName("Ganguly");
        mockCustomer.setEmail("pritam.ganguly@wipro.com");

        when(repository.save(any(Customer.class))).thenReturn(mockCustomer);
        Customer cust = service.updateCustomer(3, customer);
        System.out.println(cust);
        verify(repository,atLeastOnce()).save(customer);
        assertEquals(1, cust.getCustomerId());

    } 
  
    @Test
    @Order(6)
    void testCreateCustomerAccounts() throws Exception {

        Customer customer = new Customer(3,"Tim","Paine","tim.paine@wipro.com");

        Set<Account> customerAccounts = new HashSet<Account>();        
        customer.setAccounts(customerAccounts);

        Optional<Customer> customers = Optional.of(customer);
        when(repository.findById(any(Long.class))).thenReturn(customers);

        Account account = new Account();
        account.setAccountType("Savings");
        account.setBalance(2000);

        Customer mockCustomer = new Customer();
        mockCustomer.setCustomerId(1);
        mockCustomer.setFirstName("Pritam");
        mockCustomer.setLastName("Ganguly");
        mockCustomer.setEmail("pritam.ganguly@wipro.com");

        Account mockAccount = new Account();
        mockAccount.setAccountNumber(1);
        mockAccount.setAccountType("Savings");
        mockAccount.setBalance(7000);

        Set<Account> mockCustomerAccounts = new HashSet<Account>();
        mockCustomerAccounts.add(mockAccount);

        mockCustomer.setAccounts(mockCustomerAccounts);

        when(repository.save(any(Customer.class))).thenReturn(mockCustomer);
        Customer cust = service.createCustomerAccounts(3, account);
        System.out.println(cust);
        verify(repository,atLeastOnce()).save(customer);
        assertEquals(1, cust.getCustomerId());
    } 
  
    @Test
    @Order(7)
    void testAddCustomerAccounts() throws Exception {
        Customer customer = new Customer(3,"Tim","Paine","tim.paine@wipro.com");

        Set<Account> customerAccounts = new HashSet<Account>();        
        customer.setAccounts(customerAccounts);

        Optional<Customer> customers = Optional.of(customer);
        when(repository.findById(any(Long.class))).thenReturn(customers);

        Account account =  new Account();
        account.setAccountNumber(2);
        account.setAccountType("Savings");
        account.setBalance(2000);

        Customer mockCustomer = new Customer();
        mockCustomer.setCustomerId(1);
        mockCustomer.setFirstName("Pritam");
        mockCustomer.setLastName("Ganguly");
        mockCustomer.setEmail("pritam.ganguly@wipro.com");

        Account mockAccount = new Account();
        mockAccount.setAccountNumber(1);
        mockAccount.setAccountType("Savings");
        mockAccount.setBalance(7000);

        Set<Account> mockCustomerAccounts = new HashSet<Account>();
        mockCustomerAccounts.add(mockAccount);

        mockCustomer.setAccounts(mockCustomerAccounts);

        when(repository.save(any(Customer.class))).thenReturn(mockCustomer);
        Customer cust = service.addCustomerAccounts(3, account);
        System.out.println(cust);
        verify(repository,atLeastOnce()).save(customer);
        assertEquals(1, cust.getCustomerId());
        Set<Account> tempAccounts = cust.getAccounts();
        if(tempAccounts != null) {
            for(Account tempAccount: tempAccounts) {
                assertEquals(1, tempAccount.getAccountNumber());
            }
        }
    }

    @Test
    @Order(8)
    void testUpdateCustomer_NotFound() throws Exception {
        Optional<Customer> customers = Optional.of(new Customer(3,"Tim","Paine","tim.paine@wipro.com"));
        Customer customer = customers.get();
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception customerNotFoundEx = assertThrows(NotFoundException.class, () -> {
            service.updateCustomer(3, customer);
        });

        assertTrue(customerNotFoundEx.getMessage().contains("Customer with id-->3 not found."));
    } 
  
}

