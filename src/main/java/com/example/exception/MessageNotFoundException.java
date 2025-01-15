package com.example.exception;

public class MessageNotFoundException extends RuntimeException{

    public MessageNotFoundException (int id) {
        super("Message not found" + id);
    }
}