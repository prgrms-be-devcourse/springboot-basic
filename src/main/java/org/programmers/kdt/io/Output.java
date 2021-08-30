package org.programmers.kdt.io;

import java.util.List;

public interface Output {
    void inputError(String errorMessage);
    void printSuccessMessage(Object obj);
    void sayGoodBye();
    void print(String message);
}
