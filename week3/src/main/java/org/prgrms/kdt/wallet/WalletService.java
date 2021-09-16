package org.prgrms.kdt.wallet;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;

import java.util.List;
import java.util.UUID;

public interface WalletService {
    int insert(UUID walletId, Voucher voucher);

    int insertWalletByCustomerId(UUID customerId);

    List<Voucher> findVouchersByCustomerId(UUID customerId);

    void deleteByCustomerId(UUID customerId);

    List<Customer> findCustomersByVoucherType(VoucherType voucherType);

    List<Wallet> finsWalletsByCustomerId(UUID customerId);

}
