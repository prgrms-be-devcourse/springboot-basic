package com.example.voucherproject.common.console;

import com.example.voucherproject.common.enums.*;
import com.example.voucherproject.user.enums.UserMenu;
import com.example.voucherproject.user.enums.UserType;
import com.example.voucherproject.voucher.enums.VoucherMenu;
import com.example.voucherproject.voucher.enums.VoucherType;
import com.example.voucherproject.wallet.enums.WalletMenu;

public interface Input {
    /* Common */
    ServiceType selectService();

    /* User */
    String userName();
    UserType userType();
    UserType isBlacklist();
    UserMenu selectUserMenu(ServiceType type);
    int selectUser(int size);

    /* Voucher */
    Long amount();
    VoucherMenu selectVoucherMenu(ServiceType type);
    VoucherType createVoucher();
    int selectVoucher(int size);


    /* Wallet */
    WalletMenu selectWalletMenu(ServiceType walletService);
}
