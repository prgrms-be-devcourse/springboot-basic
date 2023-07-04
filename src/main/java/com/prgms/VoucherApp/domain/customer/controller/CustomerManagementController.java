package com.prgms.VoucherApp.domain.customer.controller;

import com.prgms.VoucherApp.domain.customer.CustomerCommand;
import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;
import com.prgms.VoucherApp.domain.customer.model.CustomerDao;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerManagementApp implements Runnable {

    private final CustomerDao customerDao;
    private final Input input;
    private final Output output;

    public CustomerManagementApp(CustomerDao customerDao, Input input, Output output) {
        this.customerDao = customerDao;
        this.input = input;
        this.output = output;
    }


    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            output.printCustomerCommand();
            CustomerCommand customerCommand = CustomerCommand.findByCustomerTypeNumber(input.inputCustomerCommand());

            if (customerCommand.isFindBlackList()) {
                List<CustomerDto> blackLists = customerDao.readBlackLists();
                output.printBlackLists(blackLists);
                continue;
            }

            if (customerCommand.isExit()) {
                isRunning = false;
                continue;
            }

            output.printNotImplementMsg();
        }
    }
}
