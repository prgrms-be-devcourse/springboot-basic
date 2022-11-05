package com.example.springbootbasic.console.output;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.example.springbootbasic.console.ConsoleStatus.*;

@Component
public class ConsoleOutput {
    private static final String RESPONSE_ERROR = "응답 도중 에러가 발생하였습니다.";
    private static final String RESPONSE_EXIT = "바우처 관리 애플리케이션을 종료합니다.";

    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public void response(ResponseBody responseBody) {
        try {
            if (responseBody.getStatus() == FAIL) {
                bw.write(RESPONSE_ERROR);
            }
            if (responseBody.getStatus() == END) {
                bw.write(RESPONSE_EXIT);
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
