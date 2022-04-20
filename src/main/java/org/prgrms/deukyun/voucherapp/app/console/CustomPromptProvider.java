package org.prgrms.deukyun.voucherapp.app.console;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        String greetingMessage = "\n=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n\n";
        return new AttributedString(greetingMessage);
    }
}
