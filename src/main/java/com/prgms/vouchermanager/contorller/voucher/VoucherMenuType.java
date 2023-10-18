package com.prgms.vouchermanager.contorller.voucher;

public enum VoucherMenuType {
    CREATE(1), LIST(2);
    private final int number;

    VoucherMenuType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }


}
