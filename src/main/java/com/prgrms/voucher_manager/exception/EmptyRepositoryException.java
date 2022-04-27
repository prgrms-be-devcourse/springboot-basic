package com.prgrms.voucher_manager.exception;

public class EmptyRepositoryException extends RuntimeException{
    public EmptyRepositoryException(String message) {
        super(message);
    }
}
