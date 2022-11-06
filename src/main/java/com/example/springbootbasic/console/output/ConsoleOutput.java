package com.example.springbootbasic.console.output;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.example.springbootbasic.console.ConsoleStatus.*;
import static com.example.springbootbasic.console.output.ResponseFailMessage.RESPONSE_ERROR;
import static com.example.springbootbasic.console.output.ResponseFailMessage.RESPONSE_EXIT;

@Component
public class ConsoleOutput {

    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public void response(ResponseBody responseBody) {
        try {
            if (responseBody.getStatus() == FAIL) {
                bw.write(RESPONSE_ERROR.getMessage());
            }
            if (responseBody.getStatus() == END) {
                bw.write(RESPONSE_EXIT.getMessage());
            }
            if (responseBody.getStatus() == SUCCESS) {
                bw.write(responseBody.getBody());
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
