package com.bankProject.entity;

import java.util.Set;


 
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountNumber;

    private String accountType;

    private double balance; 

    @jakarta.persistence.ManyToMany(mappedBy = "accounts")
    @JsonIgnore
    private Set<Customer> customers;

    public Set<Customer> getCustomers() {
        return customers;
    }
 
    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
 
    public Account() {
    }

    public Account(long accountNumber, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }
 
    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
 
    @Override
    public String toString() {
        return "Account [accountNumber=" + accountNumber + ", accountType=" + accountType + ", balance=" + balance
                + "]";
    }



}
