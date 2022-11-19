package org.prgrms.kdtspringdemo.domain.wallet;

import org.prgrms.kdtspringdemo.domain.customer.model.Customer;
import org.prgrms.kdtspringdemo.domain.voucher.model.Voucher;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    List<Voucher> findVouchersByCustomerId(UUID customerId);

    List<Customer> findCustomersByVoucherId(UUID voucherId);

    List<Wallet> addVoucherToCustomer(UUID customerId, UUID voucherId);

    void deleteVoucherFromCustomer(UUID customerId, UUID voucherId);

}
