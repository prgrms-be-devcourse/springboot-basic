package org.programmers.springorder;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.consts.ErrorMessage;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.customer.controller.CustomerController;
import org.programmers.springorder.utils.MenuType;
import org.programmers.springorder.voucher.controller.VoucherConsoleController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication implements CommandLineRunner {

    private final Console console;
    private final VoucherConsoleController voucherConsoleController;
    private final CustomerController customerController;
    public VoucherApplication(Console console, VoucherConsoleController voucherConsoleController, CustomerController customerController) {
        this.console = console;
        this.voucherConsoleController = voucherConsoleController;
        this.customerController = customerController;
    }

    @Override
    public void run(String... args) {

        boolean isRunning = true;
        boolean isWeb = false;
        if(chooseMedia()){
            isWeb = true;
        };
        while(isRunning) {
            if(isWeb) break;
            MenuType menu = console.inputMenu();

            switch (menu) {
                case EXIT -> {
                    isRunning = false;
                    console.printMessage(Message.EXIT_PROGRAM_MESSAGE);
                }
                case CREATE -> voucherConsoleController.createVoucher();
                case LIST -> voucherConsoleController.getVoucherList();
                case BLACK -> customerController.printBlackList();
                case ALLOCATE -> voucherConsoleController.giveVoucher();
                case GET_OWNER_VOUCHER -> voucherConsoleController.getVouchersOfOwner();
                case DELETE_VOUCHER -> voucherConsoleController.deleteVoucher();
                case SEARCH_VOUCHER_OWNER -> customerController.getVoucherOwner();
            }
        }
    }

    private boolean chooseMedia() {
        String media = console.chooseMedia();
        if(media.equals("1")){
            return true;
        }
        if(media.equals("2")){
            return false;
        };
        throw new RuntimeException(ErrorMessage.INVALID_VALUE_MESSAGE);
    }

}
