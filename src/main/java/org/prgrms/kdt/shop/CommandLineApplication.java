package org.prgrms.kdt.shop;

import org.prgrms.kdt.shop.domain.FixedAmountVoucher;
import org.prgrms.kdt.shop.domain.PercentDiscountVoucher;
import org.prgrms.kdt.shop.enums.MenuStatus;
import org.prgrms.kdt.shop.enums.VoucherType;
import org.prgrms.kdt.shop.io.Input;
import org.prgrms.kdt.shop.io.Output;
import org.prgrms.kdt.shop.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.UUID;

public class CommandLineApplication implements ApplicationRunner {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public CommandLineApplication(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean exitFlag = true;
        while (exitFlag) {
            output.printMenu();
            MenuStatus menuStatus = inputMenu();
            switch (menuStatus) {
                case CREATE:
                    inputVoucherMenu();
                    break;
                case LIST:
                    voucherService.printAll();
                    break;
                case EXIT:
                    exitFlag = false;
            }
        }
        System.exit(0);
    }

    private void inputVoucherMenu( ) {
        VoucherType voucherStatus = inputVoucher();
        switch (voucherStatus) {
            case FIXED_AMOUNT:
                inputFixedAmount();
                break;
            case PERCENT_DISCOUNT:
                inputPercentDiscount();
                break;
        }
    }

    private String inputFixedAmount( ) {
        try {
            output.printDiscountInput();
            String inputDiscount = input.getInput();
            voucherService.addVoucher(new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(inputDiscount)));
            return inputDiscount;
        } catch (Exception e) {

            output.printInputError();
            logger.error("할인 입력 에러", e);
        }
        return inputFixedAmount();
    }

    private String inputPercentDiscount( ) {
        try {
            output.printDiscountInput();
            String inputDiscount = input.getInput();
            voucherService.addVoucher(new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(inputDiscount)));
            return inputDiscount;
        } catch (Exception e) {
            output.printInputError();
            logger.error("할인 입력 에러", e);
        }
        return inputPercentDiscount();
    }

    private VoucherType inputVoucher( ) {
        try {
            output.printVoucherSelector();
            String inputVoucher = input.getInput();
            return VoucherType.find(inputVoucher);
        } catch (IllegalArgumentException e) {
            output.printInputError();
            logger.error("바우처 입력 에러", e);
        }
        return inputVoucher();
    }

    private MenuStatus inputMenu( ) {
        try {
            output.printMenuSelector();
            String inputMenu = input.getInput();
            return MenuStatus.find(inputMenu);
        } catch (IllegalArgumentException e) {
            output.printInputError();
            logger.error("메뉴 입력 에러", e);
        }
        return inputMenu();
    }
}
