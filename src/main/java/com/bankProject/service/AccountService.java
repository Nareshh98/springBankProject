package com.bankProject.service;

import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bankProject.entity.Account;
import com.bankProject.entity.AccountType;
import com.bankProject.entity.FundTransfer;
import com.bankProject.repository.AccountRepository;

import jakarta.transaction.Transactional;
 

@Service
public class AccountService {

    @Autowired
    AccountRepository repository;

    public List<Account> getAccounts() {
        return (List<Account>) repository.findAll();
    }
 
    public ResponseEntity<Account> createAccount(Account account) throws Exception {
        if(!AccountType.isValid(account.getAccountType())) { // loan Account
            throw new Exception("Account Type -> " + account.getAccountType() + " is not valid !!!");
        }
        repository.save(account);
        return new ResponseEntity<Account>(account, HttpStatus.CREATED); 
    }
 
    public Account getAccount(long accountNumber) {
        Optional<Account> account = repository.findById(accountNumber);
        return account.get();
    }
 
    public Account updateAccount(long accountNumber, Account account) throws Exception {
        if(!AccountType.isValid(account.getAccountType())) {
            throw new Exception("Account Type -> " + account.getAccountType() + " is not valid !!!");
        }
        Optional<Account> accounts = repository.findById(accountNumber);
        Account updateAccount = accounts.get();

        if(updateAccount == null) {
            throw new Exception("Account with id-->"+ accountNumber +" not found.");
        }
        updateAccount.setAccountType(account.getAccountType());
        updateAccount.setBalance(account.getBalance());
        Account updatedAccount = repository.save(updateAccount);
        return updatedAccount;
    }
 
    public Account deleteAccount(long accountNumber) throws Exception {
        Optional<Account> accounts = repository.findById(accountNumber);
        Account deleteAccount = accounts.get();

        if(deleteAccount==null) {
            throw new Exception("Account with id-->"+ accountNumber +" not found.");
        } 

        repository.deleteById(accountNumber);
        return deleteAccount;
    }
 
    @Transactional
    public FundTransfer transferFund(FundTransfer fundTransfer) throws Exception {
        long fromAcctNUm = fundTransfer.getFromAccount().getAccountNumber();
        long toAcctNum = fundTransfer.getToAccount().getAccountNumber();

        double amtToTrasfer = fundTransfer.getAmount();

        Optional<Account> fromAccounts = repository.findById(fromAcctNUm);
        Account fromAccount = fromAccounts.get();

        Optional<Account> toAccounts = repository.findById(toAcctNum);
        Account toAccount = toAccounts.get();

        if(amtToTrasfer > fromAccount.getBalance()) {
            throw new Exception("Balance in Account " + fromAcctNUm + " is insufficient");
        } else if(amtToTrasfer < 0) {
            throw new Exception("Amount to be transferred should not be negative");
        } else if(fromAccount.getAccountNumber() == toAccount.getAccountNumber()) {
            throw new Exception("From Account and to Account cannot be same");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amtToTrasfer);
        toAccount.setBalance(toAccount.getBalance() + amtToTrasfer);

        Account updatedFromAccount = repository.save(fromAccount);
        Account updatedToAccount = repository.save(toAccount);

        FundTransfer updatedValues = new FundTransfer();
        updatedValues.setFromAccount(updatedFromAccount);
        updatedValues.setToAccount(updatedToAccount);
        updatedValues.setAmount(amtToTrasfer);

        return updatedValues;
    }
 
}
