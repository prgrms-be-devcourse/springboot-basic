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

    // https://stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given-string
    public static boolean contains(String s) {
        for (VoucherType vt : values())
            if (vt.name().equalsIgnoreCase(s))
                return true;
        return false;
    }

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
