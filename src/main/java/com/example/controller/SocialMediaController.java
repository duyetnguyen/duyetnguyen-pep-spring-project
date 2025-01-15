package com.example.controller;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;




/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Message message = messageService.getMessageById(messageId);
        if( message == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
        }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable int messageId) {
        int rowsDeleted = messageService.deleteMessageById(messageId);
        if (rowsDeleted > 0) {
            return ResponseEntity.ok(rowsDeleted);
        }else {
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Map<String, String> requestBody) {
        String updatedMessageText = requestBody.get("messageText");
        int rowsUpdated = messageService.updateMessageById(messageId, updatedMessageText);

        if (rowsUpdated > 0) {
            return new ResponseEntity<>(rowsUpdated, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(rowsUpdated, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessageByUser(accountId);
        return ResponseEntity.ok(messages);
    }
    
    @PostMapping("/messages")
    public ResponseEntity<Message> insertMessage(@RequestBody Message message) {
        
            Message newMessage = messageService.createMessage(message);
            
            if (newMessage != null) {
                return new ResponseEntity<>(newMessage, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }     
        }
  
    @PostMapping("/register")
    public ResponseEntity<Account> registerNewAccount(@RequestBody @Validated Account account) {
        try {
            Account registerAccount = accountService.registerAccount(account);
            return new ResponseEntity<>(registerAccount, HttpStatus.OK);
        } catch ( Exception e) {
            if (e instanceof org.springframework.web.server.ResponseStatusException) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account authenAccount = accountService.authenticateAccount(account.getUsername(), account.getPassword());

        if (authenAccount != null) {
            return new ResponseEntity<>(authenAccount, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}














    




    

    

   
    
    

