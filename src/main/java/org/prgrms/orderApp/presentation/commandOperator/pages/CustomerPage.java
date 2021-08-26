package org.prgrms.orderApp.presentation.commandOperator.pages;

import org.prgrms.orderApp.application.service.CustomerApplicationService;
import org.prgrms.orderApp.domain.customer.model.Customer;
import org.prgrms.orderApp.infrastructure.library.console.Console;
import org.prgrms.orderApp.presentation.commandOperator.script.AllScriptForCMDApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomerPage implements AllScriptForCMDApplication {
    @Autowired
    Console console;

    @Autowired
    CustomerApplicationService customerApplicationService;

    public void showAllBlackList(){
        console.infoMessage(showAllBlackList_GuideMessage);
        List<Customer> blackList = customerApplicationService.getAllBlackList();
        if (blackList.isEmpty()){
            console.infoMessage(emptyData);
        } else {
            console.showList(blackList);
        }

    }
}
