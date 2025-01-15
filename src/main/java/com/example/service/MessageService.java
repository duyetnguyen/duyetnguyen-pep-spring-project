package com.example.service;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;
  
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //get all messages from message table
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    //get message by message Id from message table
    public Message getMessageById (int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    //delete a message by message Id
    public int deleteMessageById(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    //update a message by message Id
    public int updateMessageById(int messageId, String updatedMessageText) {
        //construct the conditions to update message
        if (updatedMessageText == null || updatedMessageText.trim().isEmpty() || updatedMessageText.length() > 255) {
            return 0;
        }
        Message message = messageRepository.findById(messageId).orElse(null);   //message objec to update
        if (message != null) {
            message.setMessageText(updatedMessageText);     //set messageText to new message text
            messageRepository.save(message);                 //update new message to database
            return 1;    // update successfully
        }
        return 0;    // Message not found
    }
    
    //get messge from user given postedBy
    public List<Message> getMessageByUser(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    //create new message
    public Message createMessage(Message message) {
        //construct conditions
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty() 
        || message.getMessageText().length() > 255 ||!accountRepository.existsById(message.getPostedBy()))
        {
            return null;
        }
        
        Message saveMessage = messageRepository.save(message);
        return saveMessage;
    }
    



}    










    
  


    





    


  



