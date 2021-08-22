package org.prgrms.orderapp.model;

public enum VoucherType {
    FIXED {
        @Override
        public boolean isValidAmount(long amount) {
            return amount >= 0;
        }
    },
    PERCENT {
        @Override
        public boolean isValidAmount(long amount) {
            return 0 <= amount && amount <= 100;
        }
    };

    public static boolean isValid(String voucherType, String amount) {
        try {
            long value = Long.parseLong(amount);
            if (VoucherType.valueOf(voucherType).isValidAmount(value)) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public abstract boolean isValidAmount(long amount);
}
