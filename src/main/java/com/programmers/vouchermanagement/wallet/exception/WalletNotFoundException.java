package com.programmers.vouchermanagement.wallet.exception;

public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException() {
        super("Wallet not found. ");
    }
}
