package team.marco.voucher_management_system.view.consoleapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.view.consoleapp.management.ManagementApplication;
import team.marco.voucher_management_system.view.consoleapp.wallet.WalletApplication;

import java.io.UncheckedIOException;

import static team.marco.voucher_management_system.view.consoleapp.ConsoleMessage.*;

@Component
public class ConsoleVoucherApplication implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleVoucherApplication.class);
    
    private final WalletApplication walletApplication;
    private final ManagementApplication managementApplication;

    private Boolean isRunning;

    public ConsoleVoucherApplication(WalletApplication walletApplication, ManagementApplication managementApplication) {
        this.walletApplication = walletApplication;
        this.managementApplication = managementApplication;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            provideLauncher();
        }
    }

    private void provideLauncher() {
        try {
            provideServiceManual();
            ServiceType input = getServiceRequest();
            handleServiceRequest(input);
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void provideServiceManual() {
        ConsoleUtil.print(MAIN_HEADER);

        for(ServiceType type : ServiceType.values()) {
            ConsoleUtil.print(type.getManual());
        }

        ConsoleUtil.println();
    }

    public ServiceType getServiceRequest() {
        ConsoleUtil.print(SELECT_SERVICE);
        int input = ConsoleUtil.readInt();

        return ServiceType.get(input);
    }

    public void handleServiceRequest(ServiceType requestedServiceType) {
        switch (requestedServiceType) {
            case MANAGEMENT -> runManagementApplication();
            case WALLET -> runWalletApplication();
            case EXIT -> exitProgram();
        }
    }

    private void runManagementApplication() {
        managementApplication.run();
    }

    private void runWalletApplication() {
        walletApplication.run();
    }

    private void exitProgram() {
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

        exitProgram();
    }
}
