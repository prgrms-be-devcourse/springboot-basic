package com.example.springbootbasic.console.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.example.springbootbasic.console.output.ResponseFailMessage.RESPONSE_ERROR;
import static com.example.springbootbasic.console.output.ResponseFailMessage.RESPONSE_EXIT;

@Component
public class ConsoleOutput {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public void response(ResponseBody responseBody) {
        try {
            if (responseBody.isFail()) {
                bw.write(RESPONSE_ERROR.getMessage());
            }
            if (responseBody.isEnd()) {
                bw.write(RESPONSE_EXIT.getMessage());
            }
            if (responseBody.isSuccess() || responseBody.isAgain()) {
                bw.write(responseBody.getBody());
            }
            bw.flush();
        } catch (IOException e) {
            StackTraceElement[] tackTraceElement = e.getStackTrace();
            String className = tackTraceElement[0].getClassName();
            String methodName = tackTraceElement[0].getMethodName();
            int lineNumber = tackTraceElement[0].getLineNumber();
            String fileName = tackTraceElement[0].getFileName();

            logger.error("[{}] className => {}, methodName => {}, lineNumber => {}, fileName => {}",
                    responseBody.getStatus(),
                    className,
                    methodName,
                    lineNumber,
                    fileName);
        }
    }
}