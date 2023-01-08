package com.bankProject.repository;

import org.springframework.data.repository.CrudRepository;

import com.bankProject.entity.Customer;


 
public interface CustomerRepository extends CrudRepository<Customer, Long> {
 
}
