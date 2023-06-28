package com.example.demo.common.io.impl;

import com.example.demo.common.io.Output;

public class ConsoleOutput implements Output {
    @Override
    public void printLine(String line) {
        System.out.println(line);
    }
}
