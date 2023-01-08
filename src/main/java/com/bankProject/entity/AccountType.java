package com.bankProject.entity;

public enum AccountType {
    Savings,Current;

    public static boolean isValid(String acctType) {
        AccountType acctTypes[] = AccountType.values(); 
        for(AccountType acct : acctTypes) {
            if(acct.toString().equals(acctType)) 
                return true;
        }
        return false;
    }
}

