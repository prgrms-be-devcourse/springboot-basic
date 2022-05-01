package com.mountain.voucherApp.shared.io;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import static com.mountain.voucherApp.shared.constants.CommonCharacter.INPUT_PROMPT;

@Component
public class InputConsole implements Input {

    public static final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public String input() {
        String command = textIO.newStringInputReader()
                .read(INPUT_PROMPT);
        return command;
    }

}
