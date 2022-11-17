package com.programmers;

import com.programmers.customer.controller.CustomerController;
import com.programmers.view.View;
import com.programmers.voucher.controller.VoucherController;
import com.programmers.voucher.menu.Menu;
import com.programmers.wallet.controller.WalletController;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.programmers.message.Message.GREETING_MESSAGE;
import static com.programmers.voucher.menu.Menu.findMenu;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CommandLineApplication implements Runnable {
    private final Logger logger = getLogger(CommandLineApplication.class);
    private final View view;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    @Autowired
    public CommandLineApplication(View view, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.view = view;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    @Override
    public void run() {
        String userCommand = "";

        while (!userCommand.equals(Menu.EXIT.name())) {

            view.printMessage(GREETING_MESSAGE);
            userCommand = view.getUserCommand().toUpperCase();

            try {

                Menu userMenu = findMenu(userCommand);
                executeUserCommand(userMenu);

            } catch (Exception e) {
                logger.error("사용자 커맨드 입력값 오류", e);
                view.printMessage(e.getMessage());
            }
        }
    }

    private void executeUserCommand(Menu userMenu) {
        switch (userMenu) {
            case JOIN:
                customerController.join();
                break;

            case CUSTOMERS:
                customerController.findAllCustomers();
                break;

            case REGISTER:
                voucherController.createVoucher();
                break;

            case VOUCHERS:
                voucherController.showVoucherList();
                break;

            case ASSIGN:
                walletController.assign();
                break;

            case WALLET:
                walletController.showCustomerVoucher();
                break;

            case VOUCHER_OWNER:
                walletController.findVoucherOwner();
                break;

            case DELETE_W:
                walletController.delete();
                break;

            case EXIT:
                return;
        }
    }
}
