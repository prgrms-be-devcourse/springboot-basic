package org.programmers.VoucherManagement;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.io.CommandExecutor;
import org.programmers.VoucherManagement.io.Console;
import org.programmers.VoucherManagement.io.ConsoleMessage;
import org.programmers.VoucherManagement.io.MenuType;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class VoucherManagementRunner implements CommandLineRunner {
    private final Console console;
    private final CommandExecutor commandExecutor;
    private final Logger logger = LoggerFactory.getLogger(VoucherManagementRunner.class);

    @Override
    public void run(String... args) {
        MenuType menuType;
        boolean isRunning = true;

        while (isRunning) {
            try {
                console.printConsoleMessage(ConsoleMessage.START_TYPE_MESSAGE);
                menuType = MenuType.from(console.readType());

                if (menuType == MenuType.EXIT) {
                    isRunning = false;
                }
                commandExecutor.execute(menuType);

            } catch (VoucherException | IllegalArgumentException e) {
                logger.info(e.getMessage());
            }
        }
    }

}
