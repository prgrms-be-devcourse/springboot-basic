package com.programmers.springbootbasic.presentation.view;

import java.io.IOException;

public interface Output {
    void print(String input) throws IOException;

    void printLine(String input) throws IOException;

}
