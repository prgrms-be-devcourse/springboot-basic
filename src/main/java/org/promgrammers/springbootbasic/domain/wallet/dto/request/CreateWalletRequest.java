package org.promgrammers.springbootbasic.domain.wallet.dto.request;

import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;

public record CreateWalletRequest(Voucher voucher, Customer customer) {

}
