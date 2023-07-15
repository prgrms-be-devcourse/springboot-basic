package com.devcourse.springbootbasic.application.wallet.repository;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;

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
