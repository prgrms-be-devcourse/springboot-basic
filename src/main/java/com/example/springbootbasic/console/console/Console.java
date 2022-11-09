package com.example.springbootbasic.console.console;

import com.example.springbootbasic.console.input.ConsoleInput;
import com.example.springbootbasic.console.input.RequestBody;
import com.example.springbootbasic.console.output.ConsoleOutput;
import com.example.springbootbasic.console.output.ResponseBody;
import com.example.springbootbasic.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.springbootbasic.console.ResponseType.FAIL;
import static com.example.springbootbasic.console.ResponseType.SUCCESS;
import static com.example.springbootbasic.console.console.ConsoleType.*;

@Component
public class Console {
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;
    private final MainController mainController;

    @Autowired
    public Console(ConsoleInput consoleInput, ConsoleOutput consoleOutput, MainController mainController) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.mainController = mainController;
    }

    public ResponseBody request() {
        ResponseBody responseBody = new ResponseBody();
        RequestBody requestBody = consoleInput.request();
        try {
            String request = requestBody.getBody();
            Optional<ConsoleType> findConsoleType = findConsoleTypeBy(request);

            if (findConsoleType.isPresent()) {
                ConsoleType consoleType = findConsoleType.get();
                requestBody.setConsoleType(consoleType);
                responseBody = mainController.request(requestBody);
                consoleOutput.response(responseBody);
            }
            if (findConsoleType.isEmpty()) {
                responseBody.setStatus(FAIL);
                consoleOutput.response(responseBody);
            }

            if (responseBody.isAgain()) {
                RequestBody newRequestBody = consoleInput.request();
                newRequestBody.setConsoleType(responseBody.getConsoleType());
                responseBody = mainController.request(newRequestBody);
                consoleOutput.response(responseBody);
            }
        } catch (RuntimeException e) {
            logger.error("[{}] - request {}", requestBody.getStatus(), requestBody.getConsoleType());
            logger.error("[{}] - response {}", responseBody.getStatus(), responseBody.getConsoleType());
        }
        return responseBody;
    }

    public void requestMainMenus() {
        RequestBody voucherMenuRequest = new RequestBody();
        RequestBody customerMenuRequest = new RequestBody();
        voucherMenuRequest.setConsoleType(VOUCHER_MENU);
        customerMenuRequest.setConsoleType(CUSTOMER_MENU);
        voucherMenuRequest.setStatus(SUCCESS);
        customerMenuRequest.setStatus(SUCCESS);

        ResponseBody voucherResponse = mainController.request(voucherMenuRequest);
        ResponseBody customerResponse = mainController.request(customerMenuRequest);

        consoleOutput.response(voucherResponse);
        consoleOutput.response(customerResponse);
    }
}
