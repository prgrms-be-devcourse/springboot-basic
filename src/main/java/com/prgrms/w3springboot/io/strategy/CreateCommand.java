package com.prgrms.w3springboot.io.strategy;

import com.prgrms.w3springboot.io.Input;
import com.prgrms.w3springboot.io.Output;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherType;
import com.prgrms.w3springboot.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CreateCommand implements CommandStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        boolean flag = true;
        while (flag) {
            output.printTypeChoice();
            String voucherType = input.input();
            output.printDiscountAmountChoice();
            String discountAmount = input.input();

            Voucher createdVoucher;
            try {
                createdVoucher = voucherService.createVoucher(UUID.randomUUID(), VoucherType.of(voucherType), Long.parseLong(discountAmount));
                output.printVoucher(createdVoucher);
                flag = false;
            } catch (NumberFormatException e) {
                logger.error("잘못된 형식을 입력받았습니다. - input : {}", discountAmount);
            } catch (IllegalArgumentException e) {
                output.printInvalidMessage();
                logger.error("{} - input : {}", e.getMessage(), voucherType);
            }
        }

        return true;
    }
}
