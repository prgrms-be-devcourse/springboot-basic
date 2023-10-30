package org.prgrms.kdtspringdemo.wallet.repository;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface WalletRepository {
    Wallet insert(Wallet wallet);
    void addVoucherByCustomerId(UUID walletId, UUID customerId, UUID voucherId);
    Optional<Wallet> findById(UUID voucherId);
    List<Voucher> findVouchersByCustomerId(UUID customerId);
    void deleteVoucherByVoucherId(UUID customerId, UUID voucherId);
    Optional<Customer> findCustomerByVoucherId(UUID voucherId);
    void deleteAll();
    
}
