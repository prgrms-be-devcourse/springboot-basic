package org.prgms.kdtspringweek1.wallet.repository;

import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.wallet.entity.Wallet;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    List<Voucher> findAllVouchersByCustomerId(UUID customerId);

    void deleteByVoucherIdAndCustomerId(UUID voucherId, UUID customerId);

    List<Customer> findAllCustomersByVoucherId(UUID voucherId);
}
