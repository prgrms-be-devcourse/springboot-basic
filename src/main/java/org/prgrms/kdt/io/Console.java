package org.prgrms.kdt.io;

import org.springframework.stereotype.Service;

import static org.prgrms.kdt.utils.Constants.COMMAND_ERROR_PROMPT;
import static org.prgrms.kdt.utils.Constants.COMMAND_LIST_PROMPT;

@Service
public class Console {
    private final Input input;
    private final Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void printCommands() {
        output.printText(COMMAND_LIST_PROMPT);
    }

    public void printCommandError() {
        output.printText(COMMAND_ERROR_PROMPT);
    }

    public String getInput() {
        return input.inputText();
    }



}
