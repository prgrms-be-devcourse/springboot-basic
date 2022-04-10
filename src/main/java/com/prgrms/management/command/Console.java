package com.prgrms.management.command;

import com.prgrms.management.command.io.Input;
import com.prgrms.management.command.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Console implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final Input input;
    private final Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while(true) {
            System.out.println(output.InputCommand());
            try {
                String inputCommand = input.readLine();
                Command parse = Command.parse(inputCommand);
                logger.info("Choose Command: {}", parse.name());
                choose(parse);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void choose(Command parse) {
        switch (parse) {
            case CREATE:
                System.out.println("parse = " + parse);
                break;
            case LIST:
                System.out.println("parse = " + parse);
                break;
            case EXIT:
                System.exit(0);
        }
    }
}
