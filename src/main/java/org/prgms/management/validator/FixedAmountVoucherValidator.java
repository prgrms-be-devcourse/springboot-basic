package org.prgms.management.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class FixedAmountVoucherValidator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int MAX_AMOUNT = 100000;
    private static final int ZERO_AMOUNT = 0;
    private static final int MAX_LENGTH = 100;
    private static final int ZERO_LENGTH = 0;

    public Boolean validate(String name, int amount) {
        if (name.length() == ZERO_LENGTH) {
            getErrorMsg(MessageFormat.format(
                    "VoucherNameLength is not to be {0}", ZERO_AMOUNT));
            return false;
        }

        if (name.length() == MAX_LENGTH) {
            getErrorMsg(MessageFormat.format(
                    "VoucherNameLength is not to be over {0}", MAX_LENGTH));
            return false;
        }

        if (amount == ZERO_AMOUNT) {
            getErrorMsg(MessageFormat.format(
                    "DiscountAmount is not to be {0}", ZERO_AMOUNT));
            return false;
        }

        if (amount > MAX_AMOUNT) {
            getErrorMsg(MessageFormat.format(
                    "DiscountAmount is not be over {0}", MAX_AMOUNT));
            return false;
        }

        return true;
    }

    private void getErrorMsg(String errorMsg) {
        logger.error(errorMsg);
        System.out.println(errorMsg);
    }
}
