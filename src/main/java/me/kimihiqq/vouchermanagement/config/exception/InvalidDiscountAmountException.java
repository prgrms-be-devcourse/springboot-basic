package me.kimihiqq.vouchermanagement.config.exception;

public class InvalidDiscountAmountException extends RuntimeException {
    public InvalidDiscountAmountException(String message) {
        super(message);
    }
}