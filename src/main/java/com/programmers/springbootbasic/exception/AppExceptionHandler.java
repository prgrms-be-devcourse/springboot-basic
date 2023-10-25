package com.programmers.springbootbasic.exception;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import com.programmers.springbootbasic.exception.exceptionClass.FileIOException;
import com.programmers.springbootbasic.exception.exceptionClass.SystemException;
import com.programmers.springbootbasic.exception.exceptionClass.UserException;
import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import com.programmers.springbootbasic.mediator.ConsoleResponse;
import com.programmers.springbootbasic.mediator.RequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);
    private final RequestProcessor requestProcessor;
    private final ConfigurableApplicationContext context;

    public AppExceptionHandler(RequestProcessor requestProcessor,
        ConfigurableApplicationContext context) {
        this.requestProcessor = requestProcessor;
        this.context = context;
    }

    public void handle() {
        try {
            requestProcessor.run();
        } catch (SystemException e) {
            logger.error(String.format("System Error : %s", e.getMessage()));
            requestProcessor.sendResponse(
                ConsoleResponse.createNoBodyResponse(e.getMessage())
            );
            handleExit(e);
        } catch (FileIOException e) {
            logger.error(String.format("FileIO Error : %s", e.getMessage()));
            requestProcessor.sendResponse(
                ConsoleResponse.createNoBodyResponse(e.getMessage())
            );
        } catch (CustomException e) {
            logger.error(String.format("Custom Error : %s", e.getMessage()));
            requestProcessor.sendResponse(
                ConsoleResponse.createNoBodyResponse(e.getMessage())
            );
        } catch (VoucherException e) {
            logger.error(String.format("Voucher Error : %s", e.getMessage()));
            requestProcessor.sendResponse(
                ConsoleResponse.createNoBodyResponse(e.getMessage())
            );
        } catch (UserException e) {
            logger.error(String.format("User Error : %s", e.getMessage()));
            requestProcessor.sendResponse(
                ConsoleResponse.createNoBodyResponse(e.getMessage())
            );
        } catch (Exception e) {
            logger.error(String.format("Unknown Error : %s", e.getMessage()));
            requestProcessor.sendResponse(
                ConsoleResponse.createNoBodyResponse(e.getMessage())
            );
        }
    }

    private void handleExit(SystemException e) {
        if (e.getErrorCode().equals(ErrorCode.EXIT)) {
            context.close();
        }
    }
}
