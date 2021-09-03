package org.programmers.kdt.io;

public interface Output {
    void inputError(String errorMessage);
    void printSuccessMessage(Object obj);
    void sayGoodBye();
    void print(String message);
}
