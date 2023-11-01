package team.marco.voucher_management_system.view.consoleapp.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.controller.customer.CustomerController;
import team.marco.voucher_management_system.controller.voucher.VoucherController;
import team.marco.voucher_management_system.view.consoleapp.ConsoleUtil;

import static team.marco.voucher_management_system.view.consoleapp.ConsoleMessage.*;

@Component
public class WalletApplication {
    private static final Logger logger = LoggerFactory.getLogger(WalletApplication.class);

    private final VoucherController voucherController;
    private final CustomerController customerController;

    private Boolean isRunning;

    public WalletApplication(VoucherController voucherController, CustomerController customerController) {
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.isRunning = true;
    }

    public void run() {
        ConsoleUtil.print(CUSTOMER_ID_REQUEST);
        String customerId = ConsoleUtil.readString();

        if(!customerController.isExistCustomer(customerId)) {
            throw new IllegalArgumentException(CUSTOMER_ID_INVALID);
        }

        while (isRunning) {
            try {
                selectCommand(customerId);
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    private void selectCommand(String customerId) {
        ConsoleUtil.print(WALLET_HEADER);

        for(WalletCommandType type : WalletCommandType.values()) {
            ConsoleUtil.print(type.getInfo());
        }

        ConsoleUtil.println();

        ConsoleUtil.print(SELECT_SERVICE);
        int input = ConsoleUtil.readInt();

        WalletCommandType commandType = WalletCommandType.get(input);
        switch (commandType) {
            case REGISTER -> registerVoucher(customerId);
            case LIST -> getMyVouchers(customerId);
            case REMOVE -> removeVoucher();
            case BACK -> backToMainApplication();
        }
    }

    private void getMyVouchers(String customerId) {
        // TODO
    }

    private void registerVoucher(String customerId) {
        // TODO
    }

    private void removeVoucher() {
        ConsoleUtil.print(VOUCHER_ID_REQUEST);
        String voucherId = ConsoleUtil.readString();
        voucherController.deleteVoucher(voucherId);

        ConsoleUtil.print(VOUCHER_DELETE_COMPLETE);
    }

    private void backToMainApplication() {
        isRunning = false;
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

        ConsoleUtil.print(e.getMessage());

        isRunning = false;
    }

}
