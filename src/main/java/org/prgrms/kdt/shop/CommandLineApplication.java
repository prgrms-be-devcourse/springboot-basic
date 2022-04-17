package org.prgrms.kdt.shop;

import org.prgrms.kdt.shop.domain.FixedAmountVoucher;
import org.prgrms.kdt.shop.domain.PercentDiscountVoucher;
import org.prgrms.kdt.shop.enums.MenuStatus;
import org.prgrms.kdt.shop.enums.VoucherChoiceNumber;
import org.prgrms.kdt.shop.io.Input;
import org.prgrms.kdt.shop.io.Output;
import org.prgrms.kdt.shop.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommandLineApplication implements ApplicationRunner {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommandLineApplication(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean exitFlag = true;
        while (exitFlag) {
            output.menu();
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
    }

    private void inputVoucherMenu( ) {
        VoucherChoiceNumber voucherStatus = inputVoucher();
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
            output.selectDiscount();
            String inputDiscount = input.input();
            voucherService.insert(new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(inputDiscount)));
            return inputDiscount;
        } catch (Exception e) {

            output.inputError();
            logger.error("할인 입력 에러");
        }
        return inputFixedAmount();
    }

    private String inputPercentDiscount( ) {
        try {
            output.selectDiscount();
            String inputDiscount = input.input();
            voucherService.insert(new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(inputDiscount)));
            return inputDiscount;
        } catch (Exception e) {
            output.inputError();
            logger.error("할인 입력 에러");
        }
        return inputPercentDiscount();
    }

    private VoucherChoiceNumber inputVoucher( ) {
        try {
            output.selectVoucher();
            String inputVoucher = input.input();
            return VoucherChoiceNumber.find(inputVoucher);
        } catch (IllegalArgumentException e) {
            output.inputError();
            logger.error("바우처 입력 에러");
        }
        return inputVoucher();
    }

    private MenuStatus inputMenu( ) {
        try {
            output.selectMenu();
            String inputMenu = input.input();
            return MenuStatus.find(inputMenu);
        } catch (IllegalArgumentException e) {
            output.inputError();
            logger.error("메뉴 입력 에러");
        }
        return inputMenu();
    }
}
