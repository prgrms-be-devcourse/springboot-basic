package com.prgms.VoucherApp.domain.wallet.dto;

import com.prgms.VoucherApp.domain.customer.dto.CustomerResponse;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResponse;
import com.prgms.VoucherApp.domain.wallet.model.Wallet;

import java.util.UUID;

public record WalletResponse(
    UUID id,
    CustomerResponse customerResDto,
    VoucherResponse voucherResDto
) {
    public WalletResponse(Wallet wallet) {
        this(wallet.getWalletId(), new CustomerResponse(wallet.getCustomer()), new VoucherResponse(wallet.getVoucher()));
    }
}
