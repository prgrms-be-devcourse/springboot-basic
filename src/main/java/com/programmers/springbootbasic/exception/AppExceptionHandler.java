package com.programmers.springbootbasic.exception;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import com.programmers.springbootbasic.exception.exceptionClass.SystemException;
import com.programmers.springbootbasic.exception.exceptionClass.UserException;
import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import com.programmers.springbootbasic.mediator.ConsoleResponse;
import com.programmers.springbootbasic.mediator.RequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AppExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);
    private final RequestProcessor requestProcessor;

    public AppExceptionHandler(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    public void handle() {
        try {
            requestProcessor.run();
        } catch (SystemException e) {
            requestProcessor.sendResponse(
                new ConsoleResponse(e.getMessage())
            );
            logger.error("System Error" + e);
            System.exit(1);
        } catch (CustomException e) {
            logger.error("Custom Error" + e);
            requestProcessor.sendResponse(
                new ConsoleResponse(e.getMessage())
            );
        } catch (VoucherException e) {
            logger.error("Voucher Error" + e);
            requestProcessor.sendResponse(
                new ConsoleResponse(e.getMessage())
            );
        } catch (UserException e) {
            logger.error("User Error" + e);
            requestProcessor.sendResponse(
                new ConsoleResponse(e.getMessage())
            );
        } catch (Exception e) {
            logger.error("Exception Error" + e);
            requestProcessor.sendResponse(
                new ConsoleResponse(e.getMessage())
            );
        }
    }
}
