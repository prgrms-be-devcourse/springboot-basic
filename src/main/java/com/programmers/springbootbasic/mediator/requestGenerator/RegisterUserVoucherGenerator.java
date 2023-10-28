package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.CreateUserVoucherWalletRequest;
import com.programmers.springbootbasic.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.presentation.MainMenu;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserVoucherGenerator implements MenuRequestGenerator {

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    public RegisterUserVoucherGenerator(ConsoleInteractionAggregator consoleInteractionAggregator) {
        this.consoleInteractionAggregator = consoleInteractionAggregator;
    }

    @Override
    public String getMenuCommand() {
        return MainMenu.CREATE_USER_VOUCHER.getCommand();
    }

    @Override
    public ConsoleRequest<CreateUserVoucherWalletRequest> generateRequest() {
        return new ConsoleRequest<>(getMenuCommand(),
            consoleInteractionAggregator.collectUserVoucherWalletInput());
    }
}
