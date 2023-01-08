package com.bankProject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when; 
  
import java.util.Optional; 
  
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bankProject.entity.Account;
import com.bankProject.entity.FundTransfer;
import com.bankProject.repository.AccountRepository; 
  
 
  
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class AccountServiceTest { 
  
    @InjectMocks
    AccountService service;
    @Mock
    private AccountRepository repository;

    @Test
    @Order(1)
    void testTransferFund() throws Exception {
        Account fromAccount = new Account(1, "Savings", 2600);
        Account toAccount = new Account(2, "Savings", 1000);
        double amount = 200;

        FundTransfer fundTransfer = new FundTransfer();
        fundTransfer.setFromAccount(fromAccount);
        fundTransfer.setToAccount(toAccount);
        fundTransfer.setAmount(amount);

        Optional<Account> fromAccounts = Optional.of(fromAccount);
        when(repository.findById(eq(Long.valueOf(1)))).thenReturn(fromAccounts);

        Optional<Account> toAccounts = Optional.of(toAccount);
        when(repository.findById(eq(Long.valueOf(2)))).thenReturn(toAccounts);

        Account mockAccount1 = new Account(1, "Current", 2400);
        Account mockAccount2 = new Account(2, "Current", 1200);

        when(repository.save(eq(fromAccount))).thenReturn(mockAccount1);
        when(repository.save(eq(toAccount))).thenReturn(mockAccount2);
        FundTransfer returnFundTransfer = service.transferFund(fundTransfer);    

        System.out.println("Test 1: returnFundTransfer from Account: " + returnFundTransfer.getFromAccount().getBalance());
        System.out.println("Test 1: returnFundTransfer to Account: " + returnFundTransfer.getToAccount().getBalance());

        assertEquals(2400, returnFundTransfer.getFromAccount().getBalance());
        assertEquals(1200, returnFundTransfer.getToAccount().getBalance());
    }

    @Test
    @Order(2)
    void testTransferFund_LowBalance() throws Exception {
        Account fromAccount = new Account(1, "Savings", 2600);
        Account toAccount = new Account(2, "Savings", 1000);
        double amount = 3000;

        FundTransfer fundTransfer = new FundTransfer();
        fundTransfer.setFromAccount(fromAccount);
        fundTransfer.setToAccount(toAccount);
        fundTransfer.setAmount(amount);

        Optional<Account> fromAccounts = Optional.of(fromAccount);
        when(repository.findById(eq(Long.valueOf(1)))).thenReturn(fromAccounts);

        Optional<Account> toAccounts = Optional.of(toAccount);
        when(repository.findById(eq(Long.valueOf(2)))).thenReturn(toAccounts);

        Exception fundTransferexception = assertThrows(Exception.class, () -> {
            service.transferFund(fundTransfer);
        });

        assertTrue(fundTransferexception.getMessage().contains("Balance in Account 1 is insufficient"));
    }

    @Test
    @Order(3)
    void testTransferFund_PositiveTransferAmount() throws Exception {
        Account fromAccount = new Account(1, "Savings", 2600);
        Account toAccount = new Account(2, "Savings", 1000);
        double amount = -100;

        FundTransfer fundTransfer = new FundTransfer();
        fundTransfer.setFromAccount(fromAccount);
        fundTransfer.setToAccount(toAccount);
        fundTransfer.setAmount(amount);

        Optional<Account> fromAccounts = Optional.of(fromAccount);
        when(repository.findById(eq(Long.valueOf(1)))).thenReturn(fromAccounts);

        Optional<Account> toAccounts = Optional.of(toAccount);
        when(repository.findById(eq(Long.valueOf(2)))).thenReturn(toAccounts);

        Exception fundTransferexception = assertThrows(Exception.class, () -> {
            service.transferFund(fundTransfer);
        });

        System.out.println("Test 3: " + fundTransferexception.getMessage());

        assertTrue(fundTransferexception.getMessage().contains("Amount to be transferred should not be negative"));
        assertEquals("Amount to be transferred should not be negative", fundTransferexception.getMessage());
    }

    @Test
    @Order(4)
    void testTransferFund_SameAccount() throws Exception {
        Account fromAccount = new Account(1, "Savings", 2600);
        Account toAccount = new Account(2, "Savings", 1000);
        double amount = 1800;

        FundTransfer fundTransfer = new FundTransfer();
        fundTransfer.setFromAccount(fromAccount);
        fundTransfer.setToAccount(toAccount);
        fundTransfer.setAmount(amount);

        Optional<Account> fromAccounts = Optional.of(fromAccount);
        when(repository.findById(eq(Long.valueOf(1)))).thenReturn(fromAccounts);

        when(repository.findById(eq(Long.valueOf(2)))).thenReturn(fromAccounts);

        Exception fundTransferexception = assertThrows(Exception.class, () -> {
            service.transferFund(fundTransfer);
        });

        assertTrue(fundTransferexception.getMessage().contains("From Account and to Account cannot be same"));
        assertEquals("From Account and to Account cannot be same", fundTransferexception.getMessage());
    } 
  
}

