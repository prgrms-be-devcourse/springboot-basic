package com.programmers.commandline.global.io;

import com.programmers.commandline.global.factory.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

@Component
public class Console {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void print(String message) {
        LoggerFactory.getLogger().info("Console print 실행");
        System.out.println(message);
    }

    public String read() {
        LoggerFactory.getLogger().info("Console read 실행");
        try {
            return br.readLine();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
