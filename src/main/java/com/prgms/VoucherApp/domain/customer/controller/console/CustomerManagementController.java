package com.prgms.VoucherApp.domain.customer.controller.console;

import com.prgms.VoucherApp.domain.customer.model.CustomerService;
import com.prgms.VoucherApp.domain.customer.model.factory.CustomerCommandStrategyFactory;
import com.prgms.VoucherApp.domain.customer.model.strategy.CustomerCommandStrategy;
import com.prgms.VoucherApp.view.CustomerCommand;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerManagementController implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(CustomerManagementController.class);
    private final CustomerService customerService;
    private final Input input;
    private final Output output;

    public CustomerManagementController(CustomerService customerService, Input input, Output output) {
        this.customerService = customerService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            try {
                output.printCustomerCommand();
                int inputCustomerNumber = input.inputCustomerCommand();
                CustomerCommand customerCommand = CustomerCommand.findByCustomerTypeNumber(inputCustomerNumber);

                if (customerCommand == CustomerCommand.EXIT) {
                    return;
                }

                CustomerCommandStrategy commandStrategy = CustomerCommandStrategyFactory.from(customerCommand);
                commandStrategy.execute(input, output, customerService);
            } catch (RuntimeException exception) {
                logger.debug("고객 관리 프로그램 실행 중 발생한 예외를 처리하였습니다.", exception);
                output.printErrorMsg(exception.getMessage());
            }
        }
    }
}
