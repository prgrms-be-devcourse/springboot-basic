package org.programmers.VoucherManagement;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.io.CommandExecutor;
import org.programmers.VoucherManagement.io.CommandType;
import org.programmers.VoucherManagement.io.Console;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class VoucherManagementRunner implements CommandLineRunner {
    private final Console console;
    private final CommandExecutor commandExecutor;

    @Override
    public void run(String... args) throws Exception {
        CommandType commandType;
        boolean isEnd = false;

        while (!isEnd) {
            console.printType();
            commandType = console.readType();

            if (commandType.isExit()){
                isEnd = true;
            }

            commandExecutor.execute(commandType);
        }
    }

}
