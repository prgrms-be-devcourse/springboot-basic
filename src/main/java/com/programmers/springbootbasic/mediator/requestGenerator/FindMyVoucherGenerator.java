package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.presentation.MainMenu;
import org.springframework.stereotype.Component;

@Component
public class FindMyVoucherGenerator implements MenuRequestGenerator {

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    public FindMyVoucherGenerator(ConsoleInteractionAggregator consoleInteractionAggregator) {
        this.consoleInteractionAggregator = consoleInteractionAggregator;
    }

    @Override
    public String getMenuCommand() {
        return MainMenu.FIND_VOUCHER_MINE.getCommand();
    }

    @Override
    public ConsoleRequest<String> generateRequest() {
        return new ConsoleRequest<>(getMenuCommand(),
            consoleInteractionAggregator.collectNicknameInput());
    }
}
