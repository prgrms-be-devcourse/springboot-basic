package com.programmers.commandline.global.io;

import com.programmers.commandline.global.aop.LogAspect;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Console {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void print(String message) {
        System.out.println(message);
    }

    public String read() {
        try {
            return br.readLine();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
