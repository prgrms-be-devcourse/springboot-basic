package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.presentation.MainMenu;
import org.springframework.stereotype.Component;

@Component
public class ViewAllBlackUserGenerator implements MenuRequestGenerator{

    @Override
    public String getMenuCommand() {
        return MainMenu.LIST_BLACK_USER.getCommand();
    }

    @Override
    public ConsoleRequest generateRequest() {
        return new ConsoleRequest(getMenuCommand());
    }
}
