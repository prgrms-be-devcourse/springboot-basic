package com.example.demo.exception;

public class WrongInputException extends Exception {
    String msg = "insert correct command";

    @Override
    public String getMessage() {
        return msg;
    }
}
