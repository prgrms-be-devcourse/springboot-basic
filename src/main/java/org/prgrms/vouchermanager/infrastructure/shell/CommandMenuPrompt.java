package org.prgrms.vouchermanager.infrastructure.shell;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CommandMenuPrompt implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("""
        === Voucher Program ===
        Type exit to exit the program.
        Type create to create a new voucher.
        Type list to list all vouchers.
        
        shell:>
        """);
    }
}
