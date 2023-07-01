package org.promgrammers.springbootbasic.domain.wallet.dto.response;

import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.wallet.model.Wallet;

import java.util.UUID;

public record WalletResponse(UUID walletId, VoucherResponse voucher, CustomerResponse customer) {

    public WalletResponse(Wallet wallet) {
        this(wallet.getWalletId(), new VoucherResponse(wallet.getVoucher()),
                new CustomerResponse(wallet.getCustomer()));
    }

    public String walletOutput() {
        return "지갑 정보 [\n" +
                customer.customerOutput() + "\n" +
                voucher.voucherOutput() + "\n";
    }
}
