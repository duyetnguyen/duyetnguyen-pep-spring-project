package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;






@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    //register new account
    public Account registerAccount(Account account) {
        if (accountRepository.findByUsername(account.getUsername()) != null) {
        throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return accountRepository.save(account);
    }
    

    //verify user login 
    public Account authenticateAccount(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
            
    }





    





}
