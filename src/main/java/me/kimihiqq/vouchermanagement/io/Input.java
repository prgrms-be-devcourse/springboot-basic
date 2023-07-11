package me.kimihiqq.vouchermanagement.io;

public interface Input {
    String readLine();
    String readLineWithPrompt(String prompt);

    long readDiscount(String prompt);
}