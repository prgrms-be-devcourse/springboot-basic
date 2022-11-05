package com.example.springbootbasic.console.input;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.springbootbasic.console.ConsoleStatus.FAIL;

@Component
public class ConsoleInput {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public RequestBody request() {
        RequestBody request = new RequestBody();

        try {
            String body = br.readLine();
            if (body.isBlank()) {
                request.setStatus(FAIL);
            }
            request.setBody(body);
        } catch (IOException e) {
            request.setStatus(FAIL);
        }

        return request;
    }
}
