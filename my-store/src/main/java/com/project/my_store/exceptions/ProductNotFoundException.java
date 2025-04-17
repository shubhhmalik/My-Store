package com.project.my_store.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message){                    //constructor
        super(message);
    }
}
