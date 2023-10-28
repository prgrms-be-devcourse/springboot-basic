package com.programmers.springbootbasic.domain.wallet.view;

import java.util.List;

public class WalletView {
    private WalletView() {
    }

    public static String getVoucherWalletView(String voucher, List<String> customers) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("--- Contain %s Voucher User list ---\n", voucher));
        for (String customer : customers) {
            sb.append(String.format("%s\n", customer));
        }
        return sb.toString();
    }

    public static String getCustomerWalletView(String customer, List<String> vouchers) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("--- %s user Vouchers ---\n", customer));
        for (String voucher : vouchers) {
            sb.append(String.format("%s\n", voucher));
        }
        return sb.toString();
    }
}
