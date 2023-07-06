package com.prgms.springbootbasic.command;

import com.prgms.springbootbasic.domain.BlackList;
import com.prgms.springbootbasic.domain.Customer;
import com.prgms.springbootbasic.util.CustomerMenu;
import com.prgms.springbootbasic.ui.Console;
import com.prgms.springbootbasic.util.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCommand.class);
    private final Console console;
    private final BlackList blackList;

    public CustomerCommand(Console console, BlackList blackList) {
        this.console = console;
        this.blackList = blackList;
    }

    public void execute() {
        String commandOfString = console.initApplication(Menu.CUSTOMER);
        CustomerMenu command = CustomerMenu.of(commandOfString);
        if (command == CustomerMenu.BLACKLIST) {
            findBlackList();
        }
    }

    private void findBlackList() {
        List<Customer> members = blackList.findBlackList();
        logger.info("블랙리스트의 고객을 조회하는데 성공했습니다. size : {}", members.size());
        console.showCustomerList(members);
    }

}
