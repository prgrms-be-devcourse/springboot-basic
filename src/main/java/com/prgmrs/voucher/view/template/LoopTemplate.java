package com.prgmrs.voucher.view.template;

import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import org.springframework.stereotype.Component;

@Component
public class LoopTemplate {
    private final ConsoleCreationWriter consoleCreationWriter;

    public LoopTemplate(ConsoleCreationWriter consoleCreationWriter) {
        this.consoleCreationWriter = consoleCreationWriter;
    }

    public void iterateUntilSuccess(CommandAction commandAction1, CommandAction commandAction2, String errorMessage) {
        boolean continueRunning = true;
        while (continueRunning) {
            commandAction1.execute();
            try {
                commandAction2.execute();
                continueRunning = false;
            } catch (NoSuchVoucherTypeException e) {
                consoleCreationWriter.write(errorMessage);
            }
        }
    }
}
