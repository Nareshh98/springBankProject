package com.bankProject.repository;

import org.springframework.data.repository.CrudRepository;

import com.bankProject.entity.Account;


 
public interface AccountRepository extends CrudRepository<Account, Long> {
 
}

