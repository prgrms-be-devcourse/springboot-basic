package com.example.springbootbasic.console.input;

import com.example.springbootbasic.VoucherConsoleApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.springbootbasic.console.ConsoleStatus.FAIL;

@Component
public class ConsoleInput {
    private static final Logger logger = LoggerFactory.getLogger(VoucherConsoleApplication.class);
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
            StackTraceElement[] tackTraceElement = e.getStackTrace();
            String className = tackTraceElement[0].getClassName();
            String methodName = tackTraceElement[0].getMethodName();
            int lineNumber = tackTraceElement[0].getLineNumber();
            String fileName = tackTraceElement[0].getFileName();

            logger.error("[ConsoleOutput] className => '{}', methodName => '{}', lineNumber => '{}', fileName => '{}'",
                    className, methodName, lineNumber, fileName);
        }

        return request;
    }
}