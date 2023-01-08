package com.bankProject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; 
  
import java.util.HashSet;
import java.util.Set; 
  
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
import com.bankProject.entity.FundTransfer;
import com.bankProject.service.AccountService; 
  

  
@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
@TestMethodOrder(OrderAnnotation.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService service;

    @Test
    @Order(1)
    void testFundTransfer() throws Exception {
        RequestBuilder request;

        Customer customer = new Customer(1,"Sam","Donald","sam.donald@wipro.com"); 
  
        Set<Account> customerAccounts = new HashSet<Account>();
        Account account1 = new Account(1, "Savings", 69800);
        Account account2 = new Account(2, "Current", 24000);
        customerAccounts.add(account1);
        customerAccounts.add(account2);
        customer.setAccounts(customerAccounts);

        Customer customer2 = new Customer(2,"Rohan","Iyer","rohan.iyer@wipro.com"); 
  
        Set<Account> customerAccounts2 = new HashSet<Account>();
        Account account3 = new Account(3, "Current", 16000);
        customerAccounts2.add(account3);
        customer2.setAccounts(customerAccounts2);

        FundTransfer fundTransfer = new FundTransfer();
        fundTransfer.setFromAccount(new Account(3, "Current", 10000));
        fundTransfer.setToAccount(new Account(2, "Current", 600));
        fundTransfer.setAmount(300);

        System.out.println("Customer :" + customer);
        System.out.println("Customer2 :" + customer2);
        when(service.transferFund(any(FundTransfer.class))).thenReturn(fundTransfer);

        String requestBody = "{\"fromAccount\":{\"accountNumber\":3,\"accountType\":\"Current\",\"balance\":16000},"
                + "\"toAccount\":{\"accountNumber\":2,\"accountType\":\"Current\",\"balance\":24000},"
                + "\"amount\":7000}";
        request = MockMvcRequestBuilders
                .put("/accounts/transferFund")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        /*String expectedResult="{\"fromAccount\":{\"accountNumber\":3,\"accountType\":\"Current\",\"balance\":9000},"
                + "\"toAccount\":{\"accountNumber\":2,\"accountType\":\"Current\",\"balance\":31000},"
                + "\"amount\":7000}";*/
        String expectedResult="{\"fromAccount\":{\"accountNumber\":3,\"accountType\":\"Current\",\"balance\":10000},"
                + "\"toAccount\":{\"accountNumber\":2,\"accountType\":\"Current\",\"balance\":600},"
                + "\"amount\":300}";
        MvcResult result = mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().json(expectedResult))
               .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(2)
    void testFundTransfer_BalanceNegetive() throws Exception {
        RequestBuilder request;

        Customer customer = new Customer(1,"Sam","Donald","sam.donald@wipro.com"); 
  
        Set<Account> customerAccounts = new HashSet<Account>();
        Account account1 = new Account(1, "Savings", 69800);
        Account account2 = new Account(2, "Current", 24000);
        customerAccounts.add(account1);
        customerAccounts.add(account2);
        customer.setAccounts(customerAccounts);

        Customer customer2 = new Customer(2,"Rohan","Iyer","rohan.iyer@wipro.com"); 
  
        Set<Account> customerAccounts2 = new HashSet<Account>();
        Account account3 = new Account(3, "Current", 16000);
        customerAccounts2.add(account3);
        customer2.setAccounts(customerAccounts2);

        FundTransfer fundTransfer = new FundTransfer();
        fundTransfer.setFromAccount(new Account(3, "Current", 10000));
        fundTransfer.setToAccount(new Account(2, "Current", 600));
        fundTransfer.setAmount(300);

        System.out.println("Customer :" + customer);
        System.out.println("Customer2 :" + customer2);
        when(service.transferFund(any(FundTransfer.class))).thenReturn(fundTransfer);

        String requestBody = "{\"fromAccount\":{\"accountNumber\":3,\"accountType\":\"Current\",\"balance\":16000},"
                + "\"toAccount\":{\"accountNumber\":2,\"accountType\":\"Current\",\"balance\":24000},"
                + "\"amount\":7000}";
        request = MockMvcRequestBuilders
                .put("/accounts/transferFund")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        /*String expectedResult="{\"fromAccount\":{\"accountNumber\":3,\"accountType\":\"Current\",\"balance\":9000},"
                + "\"toAccount\":{\"accountNumber\":2,\"accountType\":\"Current\",\"balance\":31000},"
                + "\"amount\":7000}";*/
        String expectedResult="{\"fromAccount\":{\"accountNumber\":3,\"accountType\":\"Current\",\"balance\":10000},"
                + "\"toAccount\":{\"accountNumber\":2,\"accountType\":\"Current\",\"balance\":600},"
                + "\"amount\":300}";
        MvcResult result = mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().json(expectedResult))
               .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
