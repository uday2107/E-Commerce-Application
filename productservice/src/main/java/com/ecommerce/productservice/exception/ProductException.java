package com.ecommerce.productservice.exception;

public class ProductException extends RuntimeException{

    public ProductException(String errorMessage){
        super(errorMessage);
    }

    ProductException(String message,Throwable exception){
        super(message,exception);
    }
}
