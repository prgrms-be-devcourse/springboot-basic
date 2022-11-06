package com.example.springbootbasic.console.output;

import com.example.springbootbasic.VoucherConsoleApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.example.springbootbasic.console.ConsoleStatus.*;
import static com.example.springbootbasic.console.output.ResponseFailMessage.RESPONSE_ERROR;
import static com.example.springbootbasic.console.output.ResponseFailMessage.RESPONSE_EXIT;

@Component
public class ConsoleOutput {
    private static final Logger logger = LoggerFactory.getLogger(VoucherConsoleApplication.class);

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
            StackTraceElement[] tackTraceElement = e.getStackTrace();
            String className = tackTraceElement[0].getClassName();
            String methodName = tackTraceElement[0].getMethodName();
            int lineNumber = tackTraceElement[0].getLineNumber();
            String fileName = tackTraceElement[0].getFileName();

            logger.error("[ConsoleOutput] className => {}, methodName => {}, lineNumber => {}, fileName => {}",
                    className,
                    methodName,
                    lineNumber,
                    fileName);
        }
    }
}