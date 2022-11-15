package org.programmers.spbw1.voucher.validator;

import org.programmers.spbw1.voucher.Model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherValidator {
    private final VoucherType type;
    private final String amount;
    private static final Logger logger = LoggerFactory.getLogger(VoucherValidator.class);

    public VoucherValidator(VoucherType type, String amount) {
        this.type = type;
        this.amount = amount;
    }

    public boolean validate() throws NumberFormatException{
        long discountAmount;
        try {
            discountAmount = Long.parseLong(amount);
        }catch (NumberFormatException e) {
            logger.error("NumberFormatException : " + amount);
            return false;
            // output.numFormatException();
        }

        if ((type == VoucherType.FIXED && (discountAmount < 0 || discountAmount > 10000)) ||
                (type == VoucherType.PERCENT && (discountAmount < 0 || discountAmount > 100))) {
            logger.error("invalid range ERROR, voucher type : " + type.toString() + ", tried : " + amount);
            return false;
        }
        return true;
    }
}
