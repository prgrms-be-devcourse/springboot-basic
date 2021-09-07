package com.prgrms.w3springboot.io.strategy;

import com.prgrms.w3springboot.io.Console;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherType;
import com.prgrms.w3springboot.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCommand implements CommandStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        while (true) {
            console.printTypeChoice();
            String voucherType = console.input();
            console.printDiscountAmountChoice();
            String discountAmount = console.input();

            Voucher createdVoucher;
            try {
                createdVoucher = voucherService.createVoucher(VoucherType.of(voucherType), Long.parseLong(discountAmount));
            } catch (NumberFormatException e) {
                logger.warn("잘못된 형식을 입력받았습니다. - input : {}", discountAmount);
                continue;
            } catch (IllegalArgumentException e) {
                console.printInvalidMessage();
                logger.warn("{} - input : {}", e.getMessage(), voucherType);
                continue;
            }

            console.printVoucher(createdVoucher);
            break;
        }

        return true;
    }
}
