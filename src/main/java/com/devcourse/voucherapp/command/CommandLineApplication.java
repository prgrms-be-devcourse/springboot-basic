package com.devcourse.voucherapp.command;

import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.view.CommonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineApplication implements CommandLineRunner {

    private final CommonView viewManager;
    private final VoucherCommand voucherCommand;
    private final CustomerCommand customerCommand;
    private boolean isRunning = true;

    @Override
    public void run(String... args) {
        while (isRunning) {
            viewManager.showTitle();

            try {
                String menuOption = viewManager.readMenuOption(Menu.values());
                Menu selectedMenu = Menu.from(menuOption);
                executeMenu(selectedMenu);
            } catch (Exception e) {
                String message = e.getMessage();
                log.error(message);
                viewManager.showExceptionMessage(message);
            }
        }
    }

    private void executeMenu(Menu selectedMenu) {
        switch (selectedMenu) {
            case VOUCHER -> voucherCommand.run();
            case CUSTOMER -> customerCommand.run();
            case QUIT -> quitApplication();
        }
    }

    private void quitApplication() {
        isRunning = false;
        viewManager.showQuitMessage();
    }
}
