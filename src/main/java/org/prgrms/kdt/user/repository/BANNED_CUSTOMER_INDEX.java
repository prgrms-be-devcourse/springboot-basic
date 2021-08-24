package org.prgrms.kdt.user.repository;

public enum BANNED_CUSTOMER_INDEX {
    BANNED_UUID (0), EMAIL (1), NAME (2), DESCRIPTION (3);

    BANNED_CUSTOMER_INDEX(int index) {
        this.index = index;
    }

    private final int index;

    public int value() {
        return index;
    }
}