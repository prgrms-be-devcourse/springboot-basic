package org.prgms.management.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class PercentAmountVoucherValidator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int MAX_AMOUNT = 100;
    private static final int ZERO_AMOUNT = 0;
    private static final int MAX_LENGTH = 100;
    private static final int ZERO_LENGTH = 0;

    public Boolean validate(String name, int amount) {
        if (name.length() == ZERO_LENGTH) {
            logger.error("VoucherNameLength is not to be {}", ZERO_AMOUNT);
            return false;
        }

        if (name.length() == MAX_LENGTH) {
            logger.error("VoucherNameLength is not to be over {}", MAX_LENGTH);
            return false;
        }

        if (amount == ZERO_AMOUNT) {
            logger.error("DiscountAmount is not to be {}", ZERO_AMOUNT);
            return false;
        }

        if (amount > MAX_AMOUNT) {
            logger.error("DiscountAmount is not be over {}", MAX_AMOUNT);
            return false;
        }

        return true;
    }
}
