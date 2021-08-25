package com.prgrm.kdt.customer.presentation;

import com.prgrm.kdt.command.CommandType;
import com.prgrm.kdt.customer.application.CustomerService;
import com.prgrm.kdt.customer.domain.CustomerProperties;
import com.prgrm.kdt.view.InputView;
import com.prgrm.kdt.view.OutputView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController implements Runnable{

    private final CustomerService customerService;
    private final CustomerProperties customerProperties;

    public CustomerController(CustomerService customerService, CustomerProperties customerProperties) {
        this.customerService = customerService;
        this.customerProperties = customerProperties;
    }

    @Override
    public void run() {
        while(true) {
            InputView.initCustomerMessage();
            String selectType = InputView.input();
            CommandType status = getType(selectType);
            executeMenu(status);
        }
    }

    private CommandType getType(String type) {
        return CommandType.getCommandType(type);
    }

    private void executeMenu(CommandType type) {
        if (type == CommandType.BLACK) {
            showBlackListCustomerOrder();
            return;
        }

        if (type == CommandType.EXIT) {
            exitCommandOrder();
            return;
        }

        if (type == CommandType.LIST) {
            showAllCustomerOrder();
        }
    }

    private void showAllCustomerOrder() {
        OutputView.showCustomerList(customerService.findAllCustomer());
    }

    private void showBlackListCustomerOrder() {
        OutputView.showBlackList(customerService.readBlackListFile(customerProperties.getBlackListFilePath()));
    }

    private void exitCommandOrder() {
        InputView.closeScanner();
        OutputView.exit();
    }
}
