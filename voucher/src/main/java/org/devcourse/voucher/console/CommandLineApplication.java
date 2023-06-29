package org.devcourse.voucher.console;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static java.lang.System.exit;

@Component
public class CommandLineApplication implements ApplicationRunner {
    private static boolean isRunning = true;
    private final Console console = new Console();


    @Override
    public void run(ApplicationArguments args) {
        isRunning = true;
        while (isRunning) {
            console.printMenu();
            Command commandInput = console.inputCommand();
            execute(commandInput);
        }
    }

    protected void execute(Command command) throws IllegalArgumentException {
        console.printCommandMessage(command);
        switch (command) {
            case CREATE:
                // 바우처 생성
                break;
            case LIST:
                // 바우처 조회
                break;
            case EXIT:
                stopRunning();
                break;
            default:
                throw new IllegalArgumentException("Unsupported command: " + command);
        }

    }

    private void stopRunning() {
        isRunning = false;
    }
}
