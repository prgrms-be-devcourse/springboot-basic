package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.presentation.MainMenu;
import org.springframework.stereotype.Component;

@Component
public class RegisterVoucherGenerator implements MenuRequestGenerator{

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    public RegisterVoucherGenerator(ConsoleInteractionAggregator consoleInteractionAggregator) {
        this.consoleInteractionAggregator = consoleInteractionAggregator;
    }

    @Override
    public String getMenuCommand() {
        return MainMenu.CREATE_VOUCHER.getCommand();
    }

    @Override
    public ConsoleRequest<CreateVoucherRequest> generateRequest() {
        return new ConsoleRequest<>(getMenuCommand(),consoleInteractionAggregator.collectVoucherInput());
    }
}
