package team.marco.voucher_management_system.view.consoleapp.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.controller.customer.CustomerController;
import team.marco.voucher_management_system.controller.voucher.VoucherController;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherCreateRequest;

import java.io.UncheckedIOException;

import static team.marco.voucher_management_system.domain.voucher.VoucherType.FIXED;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.PERCENT;
import static team.marco.voucher_management_system.view.consoleapp.ConsoleMessage.*;
import static team.marco.voucher_management_system.view.consoleapp.ConsoleUtil.*;

@Component
public class ManagementApplication {
    private static final Logger logger = LoggerFactory.getLogger(ManagementApplication.class);

    private final VoucherController voucherController;
    private final CustomerController userController;

    private Boolean isRunning;

    public ManagementApplication(VoucherController voucherController, CustomerController userController) {
        this.voucherController = voucherController;
        this.userController = userController;
        this.isRunning = true;
    }

    public void run() {
        while (isRunning) {
            try {
                selectCommand();
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    public void selectCommand() {
        print(MANAGEMENT_HEADER);

        for(ManagementCommandType type : ManagementCommandType.values()) {
            print(type.getInfo());
        }

        println();

        print(SELECT_SERVICE);
        int input = readInt();

        ManagementCommandType commandType = ManagementCommandType.get(input);
        switch (commandType) {
            case CREATE_VOUCHER -> createVoucher();
            case VOUCHER_LIST -> getVoucherList();
            case SEARCH_VOUCHER -> getVoucherInfo();
            case CUSTOMER_LIST -> getCustomerList();
            case BLACKLIST -> getBlacklist();
            case BACK -> close();
        }
    }

    private void getCustomerList() {
        /**
         * TODO: getCustomerList() 구현
         */
    }

    private void getVoucherInfo() {
        print(VOUCHER_ID_REQUEST);
        String voucherId = readString();

        printVoucher(voucherController.getVoucher(voucherId));
        println();
        println(INQUIRY_COMPLETE);
    }

    private void createVoucher() {
        logger.info("Call createVoucher()");

        print(VOUCHER_MANUAL);

        int selected = readInt();

        switch (selected) {
            case 1 -> createFixedAmountVoucher();
            case 2 -> createPercentDiscountVoucher();
            default -> throw new IllegalArgumentException(WRONG_INPUT);
        }
    }

    private void createPercentDiscountVoucher() {
        logger.info("Call createPercentDiscountVoucher()");

        print(DISCOUNT_PERCENT_REQUEST);
        int percent = readInt();

        VoucherCreateRequest request = new VoucherCreateRequest(PERCENT, percent);
        voucherController.createVoucher(request);

        println(VOUCHER_CREATE_COMPLETE);
    }

    private void createFixedAmountVoucher() {
        logger.info("Call createFixedAmountVoucher()");

        print(DISCOUNT_AMOUNT_REQUEST);
        int amount = readInt();

        VoucherCreateRequest request = new VoucherCreateRequest(FIXED, amount);
        voucherController.createVoucher(request);

        println(VOUCHER_CREATE_COMPLETE);
    }

    private void getVoucherList() {
        logger.info("Call getVoucherList()");

        printVoucherList(voucherController.getVouchers());
        println();
        println(INQUIRY_COMPLETE);
    }

    private void getBlacklist() {
        logger.info("Call getBlackListUsers()");

        printStringList(userController.getBlacklistInfo());
    }

    private void close() {
        isRunning = false;
    }

    private void handleException(Exception e) {
        if(e instanceof NumberFormatException) {
            print(NUMBER_REQUIRED);
            return;
        }

        if(e instanceof IllegalArgumentException) {
            print(e.getMessage());
            return;
        }

        logger.error(e.toString());

        String errorMessage = (e instanceof UncheckedIOException)? FILE_ERROR : PROGRAM_ERROR;
        print(errorMessage);

        isRunning = false;

        close();
    }
}
