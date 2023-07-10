package me.kimihiqq.vouchermanagement.config.exception;

public class InvalidDiscountRateException extends RuntimeException {
    public InvalidDiscountRateException(String message) {
        super(message);
    }
}