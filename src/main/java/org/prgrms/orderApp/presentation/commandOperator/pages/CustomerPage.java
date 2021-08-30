package org.prgrms.orderApp.presentation.commandOperator.pages;

import org.prgrms.orderApp.infrastructure.library.console.script.BasicScript;
import org.prgrms.orderApp.infrastructure.library.console.script.ForUxScript;
import org.prgrms.orderApp.presentation.commandOperator.script.ApplicationScript;
import org.prgrms.orderApp.presentation.commandOperator.script.MonguDbScript;
import org.prgrms.orderApp.presentation.commandOperator.script.WarnningAndErrorScript;
import org.prgrms.orderApp.service.CustomerApplicationService;
import org.prgrms.orderApp.domain.customer.model.Customer;
import org.prgrms.orderApp.infrastructure.library.console.Console;
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
