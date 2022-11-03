package org.prgrms.kdtspringdemo.io;

import org.springframework.stereotype.Component;

@Component
public class Console {
    private final Input input;
    private final Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public String getInputWithPrompt(String prompt){
        output.printPrompt(prompt);
        return input.getInput();
    }
    public void showMenu(){
        output.printText("=== Voucher Program ===");
        output.printText("Type exit to exit the program.");
        output.printText("Type create to create a new voucher.");
        output.printText("Type list to list all vouchers.");
    }
    public void showError(Exception e){
        output.printError(e);
    }
}
