package team.marco.voucher_management_system.view.consoleapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.view.consoleapp.management.ManagementApplication;
import team.marco.voucher_management_system.view.consoleapp.wallet.WalletApplication;

import java.io.UncheckedIOException;

import static team.marco.voucher_management_system.view.consoleapp.ConsoleMessage.*;

@Component
public class VoucherApplication {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

    private final WalletApplication walletApplication;
    private final ManagementApplication managementApplication;

    private Boolean isRunning;

    public VoucherApplication(WalletApplication walletApplication, ManagementApplication managementApplication) {
        this.walletApplication = walletApplication;
        this.managementApplication = managementApplication;
        this.isRunning = true;
    }

    public void run() {
        while (isRunning) {
            try {
                selectService();
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    public void selectService() {
        ConsoleUtil.print(MAIN_HEADER);

        for(ServiceType type : ServiceType.values()) {
            ConsoleUtil.print(type.getInfo());
        }

        ConsoleUtil.println();

        ConsoleUtil.print(SELECT_SERVICE);
        int input = ConsoleUtil.readInt();

        ServiceType type = ServiceType.get(input);
        switch (type) {
            case MANAGEMENT -> runManagementApplication();
            case WALLET -> runWalletApplication();
            case EXIT -> close();
        }
    }

    private void runWalletApplication() {
        walletApplication.run();
    }
    private void runManagementApplication() {
        managementApplication.run();
    }

    private void close() {
        logger.info("Call close()");

        isRunning = false;
        ConsoleUtil.print(PROGRAM_EXIT);
    }

    private void handleException(Exception e) {
        if(e instanceof NumberFormatException) {
            ConsoleUtil.print(NUMBER_REQUIRED);
            return;
        }

        if(e instanceof IllegalArgumentException) {
            ConsoleUtil.print(e.getMessage());
            return;
        }

        logger.error(e.toString());

        String errorMessage = (e instanceof UncheckedIOException)? FILE_ERROR : PROGRAM_ERROR;
        ConsoleUtil.print(errorMessage);

        isRunning = false;

        close();
    }
}
