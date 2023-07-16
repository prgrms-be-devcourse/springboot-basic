package com.tengerine.voucher_system.application.wallet.repository;

import com.tengerine.voucher_system.application.customer.model.Customer;
import com.tengerine.voucher_system.application.voucher.model.Voucher;
import com.tengerine.voucher_system.application.voucher.model.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    void updateVoucher(UUID voucherId, UUID customerId);

    void updateVoucher(UUID voucherId);

    List<Voucher> findAllVouchersByCustomerId(UUID customerId);

    Optional<Customer> findCustomerByVoucherId(UUID voucherId);

    List<Customer> findAllCustomersByVoucherType(VoucherType voucherType);
}
