package me.programmers.springboot.basic.springbootbasic.io;

import java.io.IOException;

public interface FileOutput {
    void fileOutput(Object object, String fileName) throws IOException;
}
