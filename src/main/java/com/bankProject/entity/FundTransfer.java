package com.bankProject.entity;

import org.springframework.stereotype.Component;

@Component
public class FundTransfer {
    Account fromAccount; 

    Account toAccount;  

    double amount;

    public Account getFromAccount() {
        return fromAccount;
    }
    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }
    public Account getToAccount() {
        return toAccount;
    }
    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
