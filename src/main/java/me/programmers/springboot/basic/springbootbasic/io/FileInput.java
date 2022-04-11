package me.programmers.springboot.basic.springbootbasic.io;

import java.io.IOException;

public interface FileInput {
    Object fileInput(String fileName) throws IOException;
}
