package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.presentation.MainMenu;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class FindUserByVoucherGenerator implements MenuRequestGenerator {

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    public FindUserByVoucherGenerator(ConsoleInteractionAggregator consoleInteractionAggregator) {
        this.consoleInteractionAggregator = consoleInteractionAggregator;
    }

    @Override
    public String getMenuCommand() {
        return MainMenu.FIND_USER_BY_VOUCHER.getCommand();
    }

    @Override
    public ConsoleRequest<UUID> generateRequest() {
        return new ConsoleRequest<>(getMenuCommand(),
            consoleInteractionAggregator.collectUUIDInput());
    }
}
