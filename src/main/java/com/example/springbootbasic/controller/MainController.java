package com.example.springbootbasic.controller;

import com.example.springbootbasic.console.input.RequestBody;
import com.example.springbootbasic.console.output.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.example.springbootbasic.console.ResponseType.FAIL;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final VoucherController voucherController;
    private final CustomerController customerController;

    @Autowired
    public MainController(VoucherController voucherController, CustomerController customerController) {
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public ResponseBody request(RequestBody requestBody) {
        ResponseBody responseBody = new ResponseBody();
        try {
            ControllerType controllerType = requestBody.getConsoleType().getControllerType();
            switch (controllerType) {
                case VOUCHER -> responseBody = voucherController.request(requestBody);
                case CUSTOMER -> responseBody = customerController.request(requestBody);
                default -> responseBody.setStatus(FAIL);
            }
        } catch (RuntimeException e) {
            logger.error("[{}] - request {}", requestBody.getStatus(), requestBody.getConsoleType());
            logger.error("[{}] - response {}", responseBody.getStatus(), responseBody.getConsoleType());
        }
        return responseBody;
    }
}
