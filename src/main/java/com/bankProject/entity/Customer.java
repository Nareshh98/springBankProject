package com.bankProject.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;


@Entity
public class Customer {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;
 
    private String firstName;
    private String lastName;
    private String email;
 
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "customer_accounts", joinColumns =@JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
    private Set<Account> accounts;
 
    public Customer() {
    }
 
    public Customer(long id, String firstName, String lastName, String email) {
        this.customerId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
 
    }
 
    public long getCustomerId() {
        return customerId;
    }
 
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public Set<Account> getAccounts() {
        return accounts;
    }
 
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
 
    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
                + email + ", accounts=" + accounts + "]";
    }
 
}

