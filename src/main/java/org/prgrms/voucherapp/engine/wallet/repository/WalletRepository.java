package org.prgrms.voucherapp.engine.wallet.repository;

import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.wallet.dto.CustomerVoucherDto;
import org.prgrms.voucherapp.engine.wallet.vo.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    Wallet insert(Wallet wallet);

    List<CustomerVoucherDto> findAll();

    Optional<CustomerVoucherDto> findById(UUID walletId);

    Optional<CustomerVoucherDto> findByBothId(UUID voucherId, UUID customerId);

    List<Voucher> findVouchersByCustomerId(UUID customerId);

    List<Customer> findCustomersByVoucherId(UUID voucherId);

    List<Customer> findAvailableCustomersByVoucherId(UUID voucherId);

    void deleteByCustomerId(UUID customerId);

    void deleteByVoucherId(UUID voucherId);

    void deleteByWalletId(UUID walletId);

    void deleteByBothId(UUID voucherId, UUID customerId);
}
