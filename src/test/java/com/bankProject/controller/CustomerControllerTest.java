package com.bankProject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; 
  
import java.util.HashSet;
import java.util.Set; 
  
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bankProject.entity.Account;
import com.bankProject.entity.Customer;
import com.bankProject.exception.NotFoundException;
import com.bankProject.service.CustomerService; 
  

  
@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
@TestMethodOrder(OrderAnnotation.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService service;

    @Test
    @Order(1)
    void testGetCustomer() throws Exception {
        RequestBuilder request;

        Customer customer = new Customer(1,"Sam","Donald","sam.donald@wipro.com"); 
  
        Set<Account> customerAccounts = new HashSet<Account>();
        customerAccounts.add(new Account(1, "Savings", 69800));
        customerAccounts.add(new Account(2, "Current", 2000));
        customer.setAccounts(customerAccounts);

        System.out.println("Response :" + customer);
        when(service.getCustomer(any(Long.class))).thenReturn(customer);
        request=MockMvcRequestBuilders
                .get("/customers/2")
                .accept(MediaType.APPLICATION_JSON);

        String expectedResult="{customerId:1,firstName:Sam,lastName:Donald,email:sam.donald@wipro.com}";
        MvcResult result = mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().json(expectedResult))
               .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    } 
  
    @Test
    @Order(2)
    void testUpdateCustomer() throws Exception {
        RequestBuilder request;

        Customer customer = new Customer(1,"Sam","Donald","sam.donald@wipro.com"); 
  
        Set<Account> customerAccounts = new HashSet<Account>();
        customerAccounts.add(new Account(1, "Savings", 69800));
        customerAccounts.add(new Account(2, "Current", 2000));
        customer.setAccounts(customerAccounts);

        System.out.println("Response :" + customer);
        when(service.updateCustomer(any(Long.class),any(Customer.class))).thenReturn(customer);

        String requestBody = "{\"customerId\":2,\"firstName\":\"Sam\",\"lastName\":\"Donald\",\"email\":\"sam.donald@wipro.com\"}";
        request = MockMvcRequestBuilders
                .put("/customers/2")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        String expectedResult="{customerId:1,firstName:Sam,lastName:Donald,email:sam.donald@wipro.com}";
        MvcResult result = mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().json(expectedResult))
               .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    } 
  
    @Test
    @Order(3)
    void testAddCustomerAccounts() throws Exception {
        RequestBuilder request;

        Customer customer = new Customer(1,"Sam","Donald","sam.donald@wipro.com"); 
  
        Set<Account> customerAccounts = new HashSet<Account>();
        customerAccounts.add(new Account(1, "Savings", 69800));
        customerAccounts.add(new Account(2, "Current", 2000));
        customer.setAccounts(customerAccounts);

        System.out.println("Response :" + customer);
        when(service.addCustomerAccounts(any(Long.class),any(Account.class))).thenReturn(customer);

        String requestBody = "{\"accountNumber\":3,\"accountType\":\"Current\",\"balance\":4000}";
        request = MockMvcRequestBuilders
                .put("/customers/2/accounts/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        String expectedResult="{customerId:1,firstName:Sam,lastName:Donald,email:sam.donald@wipro.com,"
                + "accounts:[{accountNumber:1,accountType:Savings,balance:69800},"
                + "{accountNumber:2,accountType:Current,balance:2000}]}";
        MvcResult result = mockMvc.perform(request)
               .andExpect(status().isCreated())
               .andExpect(content().json(expectedResult))
               .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @Order(4)
    void testUpdateCustomer_NotFound() throws Exception {
        RequestBuilder request;

        Customer customer = new Customer(1,"Sam","Donald","sam.donald@wipro.com"); 
  
        Set<Account> customerAccounts = new HashSet<Account>();
        customerAccounts.add(new Account(1, "Savings", 69800));
        customerAccounts.add(new Account(2, "Current", 2000));
        customer.setAccounts(customerAccounts);

        System.out.println("Response :" + customer);
        when(service.updateCustomer(any(Long.class),any(Customer.class))).thenThrow(new NotFoundException("Test Me"));

        String requestBody = "{\"customerId\":2,\"firstName\":\"Sam\",\"lastName\":\"Donald\",\"email\":\"sam.donald@wipro.com\"}";
        request = MockMvcRequestBuilders
                .put("/customers/2")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        String expectedResult="{\"status\":\"404 NOT_FOUND\",\"errorCode\":\"NOT_FOUND_EXCEPTION\",\"message\":\"Test Me\"}";
        MvcResult result = mockMvc.perform(request)
               .andExpect(status().isNotFound())
               .andExpect(content().json(expectedResult))
               .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    } 
  
}
