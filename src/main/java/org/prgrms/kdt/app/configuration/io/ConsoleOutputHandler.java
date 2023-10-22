package org.prgrms.kdt.app.configuration.io;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputHandler implements OutputHandler{

    @Override
    public void outputStartMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void outputSystemMessage(String message) {

    }
}
