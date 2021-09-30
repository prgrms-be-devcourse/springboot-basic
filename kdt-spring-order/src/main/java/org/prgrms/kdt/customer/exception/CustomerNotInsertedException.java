package org.prgrms.kdt.customer.exception;

public class CustomerNotInsertedException extends RuntimeException{
    public CustomerNotInsertedException(String errorMessage){
        super(errorMessage);
    }
}
