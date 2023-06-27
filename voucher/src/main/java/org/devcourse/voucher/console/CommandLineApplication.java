package org.devcourse.voucher.console;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static java.lang.System.exit;

@Component
public class CommandLineApplication implements ApplicationRunner {
    public static final String CREATE_COMMAND_STRING = "create";
    public static final String LIST_COMMAND_STRING = "list";
    public static final String EXIT_COMMAND_STRING = "exit";

    private final Console console = new Console();


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String commandInput;
        while (true) {
            console.printMenu();
            commandInput = console.input();
            execute(commandInput);
        }
    }

    protected void execute(String command) throws IllegalArgumentException {
        switch (command) {
            case CREATE_COMMAND_STRING:
                // 바우처 생성
                System.out.println("Create Voucher");
                break;
            case LIST_COMMAND_STRING:
                // 바우처 조회
                System.out.println("List Voucher");
                break;
            case EXIT_COMMAND_STRING:
                System.out.println("Terminate Application");
                System.exit(0);
            default:
                throw new IllegalArgumentException("Unsupported command: " + command);
        }
    }
}
