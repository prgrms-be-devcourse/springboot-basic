package org.prgrms.orderapp.model;

public enum VoucherType {
    FIXED,
    PERCENT;

    // https://stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given-string
    public static boolean contains(String s) {
        for (VoucherType vt : values())
            if (vt.name().equalsIgnoreCase(s))
                return true;
        return false;
    }

}
