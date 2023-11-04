package org.programmers.springboot.basic.domain.wallet.exception;

public class DuplicateWalletException extends RuntimeException {

    public DuplicateWalletException() {
        super("Duplicate value already exists in wallet!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
