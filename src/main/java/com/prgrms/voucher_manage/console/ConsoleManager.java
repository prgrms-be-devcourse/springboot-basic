package com.prgrms.voucher_manage.console;

import com.prgrms.voucher_manage.domain.customer.controller.CustomerController;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.voucher.controller.VoucherController;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import com.prgrms.voucher_manage.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.prgrms.voucher_manage.console.MenuType.EXIT;
import static com.prgrms.voucher_manage.console.MenuType.matchMenuType;

@Component
@RequiredArgsConstructor
public class ConsoleManager implements ApplicationRunner {
    private final OutputUtil outputUtil;
    private final InputUtil inputUtil;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private static final Logger logger = LoggerFactory.getLogger(ConsoleManager.class);

    @Override
    public void run(ApplicationArguments args) {
        MenuType menuType = null;
        do {
            try {
                outputUtil.printMenu();
                menuType = matchMenuType(inputUtil.getStringInput());
                selectMenu(menuType);
            } catch (Exception e) {
                outputUtil.printMessage(e.getMessage());
                logger.error(e.getMessage());
            }

        } while (menuType != EXIT);
    }

    public void selectMenu(MenuType menuType) throws Exception {
        if (menuType == null) {
            throw new InvalidInputException("Invalid command input.");
        }

        switch (menuType) {
            case CREATE -> createVoucher();
            case LIST -> printVouchers(voucherController.getVouchers());
            case CUSTOMER ->  printBlackList(customerController.getBlackList());
        }
    }

    public void createVoucher() throws Exception {
        outputUtil.printVoucherSelect();
        VoucherType voucherType = VoucherType.matchVoucherType(inputUtil.getStringInput());
        if (voucherType == null) {
            throw new InvalidInputException("Invalid command input.");
        }
        switch (voucherType) {
            case FIXED -> outputUtil.requestDiscountPriceInfo();
            case PERCENT -> outputUtil.requestDiscountPercentInfo();
        }
        Long discountAmount = inputUtil.getLongInput();
        voucherController.createVoucher(voucherType, discountAmount);
    }

    private void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> {
            switch (voucher.getVoucherType()) {
                case FIXED -> outputUtil.printFixedVoucherInfo(voucher);
                case PERCENT -> outputUtil.printPercentVoucherInfo(voucher);
            }
        });
    }

    private void printBlackList(List<Customer> customers) {
        customers.forEach(outputUtil::printCustomerInfo);
    }
}
