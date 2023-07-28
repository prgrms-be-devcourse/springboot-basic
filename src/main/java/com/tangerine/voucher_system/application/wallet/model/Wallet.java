package com.tangerine.voucher_system.application.wallet.model;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.voucher.model.Voucher;

import java.util.List;
import java.util.UUID;

public record Wallet(
        UUID walletId,
        UUID voucherId,
        UUID customerId
//        Customer customer,
//        List<Voucher> voucherList
) {
}
