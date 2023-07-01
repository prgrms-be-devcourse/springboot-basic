package org.promgrammers.springbootbasic.domain.wallet.dto.response;

import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;

import java.util.UUID;

public record WalletResponse(UUID walletId, Voucher voucher, Customer customer) {
}
