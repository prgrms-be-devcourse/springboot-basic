package com.programmers.springweekly.domain;

import java.util.Arrays;

public enum VoucherMenu {
    FIXED("fixed"), PERCENT("percent");

    private String menu;

    VoucherMenu(String voucherMenu) {
        this.menu = voucherMenu;
    }

    public VoucherMenu findVoucherMenu(VoucherMenu voucherMenu){
        return Arrays.stream(VoucherMenu.values())
                .filter(voucher -> voucher.menu.equals(voucherMenu))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("찾는 바우처 종류가 없습니다."));
    }
}