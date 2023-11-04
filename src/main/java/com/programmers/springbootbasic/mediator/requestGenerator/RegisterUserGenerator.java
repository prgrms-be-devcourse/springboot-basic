package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.domain.user.presentation.dto.CreateUserRequest;
import com.programmers.springbootbasic.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.presentation.MainMenu;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserGenerator implements MenuRequestGenerator{

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    public RegisterUserGenerator(ConsoleInteractionAggregator consoleInteractionAggregator) {
        this.consoleInteractionAggregator = consoleInteractionAggregator;
    }

    @Override
    public String getMenuCommand() {
        return MainMenu.CREATE_USER.getCommand();
    }

    @Override
    public ConsoleRequest<CreateUserRequest> generateRequest() {
        return new ConsoleRequest<>(getMenuCommand(), consoleInteractionAggregator.collectUserInput());
    }
}
