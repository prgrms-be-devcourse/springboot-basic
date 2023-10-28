package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.mediator.dto.UpdateVoucherMediatorRequest;
import com.programmers.springbootbasic.presentation.MainMenu;
import org.springframework.stereotype.Component;

@Component
public class UpdateVoucherGenerator implements MenuRequestGenerator {

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    public UpdateVoucherGenerator(ConsoleInteractionAggregator consoleInteractionAggregator) {
        this.consoleInteractionAggregator = consoleInteractionAggregator;
    }

    @Override
    public String getMenuCommand() {
        return MainMenu.UPDATE_VOUCHER.getCommand();
    }

    @Override
    public ConsoleRequest<UpdateVoucherMediatorRequest> generateRequest() {
        return new ConsoleRequest(getMenuCommand(),
            consoleInteractionAggregator.collectUpdateVoucherInput());
    }
}
