package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.presentation.MainMenu;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DeleteVoucherGenerator implements MenuRequestGenerator {

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    public DeleteVoucherGenerator(ConsoleInteractionAggregator consoleInteractionAggregator) {
        this.consoleInteractionAggregator = consoleInteractionAggregator;
    }

    @Override
    public String getMenuCommand() {
        return MainMenu.DELETE_VOUCHER.getCommand();
    }

    @Override
    public ConsoleRequest<UUID> generateRequest() {
        return new ConsoleRequest<>(getMenuCommand(),
            consoleInteractionAggregator.collectUUIDInput());
    }
}
