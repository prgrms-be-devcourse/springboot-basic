package org.prgrms.kdt;

import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.model.CommandType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.prgrms.kdt.utils.Constant.COMMAND_ERROR_PROMPT;
import static org.prgrms.kdt.utils.Constant.COMMAND_LIST_PROMPT;

@SpringBootApplication
public class KdtApplication implements CommandLineRunner {

    private final Console console;

    public KdtApplication(Console console) {
        this.console = console;
    }

    public static void main(String[] args) {
        SpringApplication.run(KdtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            console.printText(COMMAND_LIST_PROMPT);

            String userInput = console.getInput();

			switch (CommandType.findCommandType(userInput)) {
				case CREATE -> {}
				case LIST -> {}
				case EXIT -> {
					return;
				}
				case UNKNOWN -> {
					console.printText(COMMAND_ERROR_PROMPT);
				}
			}
        }
    }

}
