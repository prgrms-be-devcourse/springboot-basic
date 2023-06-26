package me.kimihiqq.vouchermanagement.io;

public interface Input {
    String readLine();
    String readLine(String prompt);

    long readDiscount(String prompt);



}