package org.prgrms.orderapp.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public enum VoucherType {
    FIXED {
        @Override
        public boolean isValidAmount(long amount) {
            return amount > 0;
        }
    },
    PERCENT {
        @Override
        public boolean isValidAmount(long amount) {
            return 0 <= amount && amount <= 100;
        }
    };

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    public static boolean isValid(String voucherType, String amount) {
        try {
            long value = Long.parseLong(amount);
            if (VoucherType.valueOf(voucherType.toUpperCase()).isValidAmount(value)) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {} {}", voucherType, amount, e);
        }
        return false;
    }

    public abstract boolean isValidAmount(long amount);
}
