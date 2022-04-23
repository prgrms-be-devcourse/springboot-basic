package org.prgrms.springbasic.utils.validator;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.utils.exception.NonExistentCommand;
import org.prgrms.springbasic.utils.exception.NotValidatedException;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.COMMAND_ERROR;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.PARSING_ERROR;

@Slf4j
public class VoucherValidator {

    public static VoucherType validateVoucherType(String command) {
        VoucherType voucherType;

        try {
            voucherType = VoucherType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Got non existent command: {}", command);
            throw new NonExistentCommand(COMMAND_ERROR.getMessage());
        }

        return voucherType;
    }

    public static long validateDiscountInfo(String discountInfo) {
        long discount;

        try {
            discount = Long.parseLong(discountInfo);
        } catch (NumberFormatException e) {
            log.error("Got parsing error: {}", discountInfo);
            throw new NotValidatedException(PARSING_ERROR.getMessage());
        }

        return discount;
    }

    public static List<Voucher> validateVouchers(List<Voucher> vouchers) {
        if(vouchers.size() == 0) {
            log.error("Can't find any voucher.");

            return emptyList();
        }

        return vouchers;
    }
}
