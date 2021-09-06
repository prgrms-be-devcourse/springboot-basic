package org.prgrms.kdt.wallet;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.model.Voucher;

import java.util.List;
import java.util.UUID;

public interface WalletService {
    int insert(UUID walletId, Voucher voucher);

    List<Voucher> findCustomerVoucher(UUID customerId);

    void deleteCustomerVoucher(UUID customerId);

    List<Customer> findByVoucher(String voucherType);
}
