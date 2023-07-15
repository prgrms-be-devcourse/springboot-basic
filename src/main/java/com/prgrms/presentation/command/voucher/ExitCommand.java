package com.prgrms.presentation.command.voucher;

import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.message.GuideMessage;
import com.prgrms.presentation.view.Output;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements Command {

    private final Output output;

    public ExitCommand(Output output) {
        this.output = output;
    }

    @Override
    public Power execute() {
        output.write(GuideMessage.CLOSE.toString());

        return Power.OFF;
    }

}
