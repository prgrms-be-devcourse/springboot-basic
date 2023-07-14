package com.devcourse.voucherapp.command;

import com.devcourse.voucherapp.entity.HomeMenu;
import com.devcourse.voucherapp.view.HomeView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HomeCommand implements CommandLineRunner {

    private final HomeView commonView;
    private final VoucherCommand voucherCommand;
    private final CustomerCommand customerCommand;
    private boolean isRunning = true;

    @Override
    public void run(String... args) {
        while (isRunning) {
            commonView.showMenu();

            try {
                String menuOption = commonView.readUserInput();
                HomeMenu selectedMenu = HomeMenu.from(menuOption);
                executeMenu(selectedMenu);
            } catch (Exception e) {
                String message = e.getMessage();
                log.error(message);
                commonView.showExceptionMessage(message);
            }
        }
    }

    private void executeMenu(HomeMenu selectedMenu) {
        switch (selectedMenu) {
            case VOUCHER -> voucherCommand.run();
            case CUSTOMER -> customerCommand.run();
            case QUIT -> quitApplication();
        }
    }

    private void quitApplication() {
        isRunning = false;
        commonView.showQuitMessage();
    }
}
