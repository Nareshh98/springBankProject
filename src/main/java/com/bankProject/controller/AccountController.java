package com.bankProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bankProject.entity.FundTransfer;
import com.bankProject.service.AccountService;
 

@RestController
public class AccountController {
    @Autowired
    AccountService acctService;

    @GetMapping("/hello")
    public String hello() {
        return "Say hello to Spring Boot";
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) throws Exception {
        return acctService.createAccount(account);
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return acctService.getAccounts();
    }



    @RequestMapping(value="/accounts/{id}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable("id") int accountNumber) {
        Account account = acctService.getAccount(accountNumber);
        return account;

    }

    @PutMapping("/accounts/{id}")
    public Account updateAccount(@PathVariable int id, @RequestBody Account account) throws Exception {
        Account updatedAccount = acctService.updateAccount(id, account);
        return updatedAccount;

    }

    @DeleteMapping("/accounts/{id}")
    public Account deleteAccount(@PathVariable long id) throws Exception {
        Account deletedAccount = acctService.deleteAccount(id);
        return deletedAccount;

    }

    @PutMapping("/accounts/transferFund")
    public FundTransfer transferFund(@RequestBody FundTransfer fundTransfer) throws Exception{
        FundTransfer fundTransferred = acctService.transferFund(fundTransfer);
        return fundTransferred;

    }
}

