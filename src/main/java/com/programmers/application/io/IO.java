package com.programmers.application.io;

import java.io.IOException;

public interface IO {
    void write(String input) throws IOException;

    String read() throws IOException;
}
