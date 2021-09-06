package org.prgrms.orderApp.commandOperator.pages;

import org.prgrms.orderApp.commandOperator.script.ApplicationScript;
import org.prgrms.orderApp.commandOperator.service.CustomerApplicationService;
import org.prgrms.orderApp.customer.Customer;
import org.prgrms.orderApp.util.library.console.Console;
import org.prgrms.orderApp.util.library.console.script.BasicScript;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerPage {
    private Console console;
    private CustomerApplicationService customerApplicationService;

    public CustomerPage(Console console, CustomerApplicationService customerApplicationService){
        this.console = console;
        this.customerApplicationService = customerApplicationService;
    }
    public void showAllBlackList(){
        console.infoMessage(ApplicationScript.SHOW_ALL_BLACKLIST__GUIDE_MESSAGE);
        List<Customer> blackList = customerApplicationService.getAllBlackList();
        if (blackList.isEmpty()){
            console.infoMessage(BasicScript.EMPTY_DATA);
        } else {
            console.showList(blackList);
        }

    }
}
