package com.prgrms.vouchermanagement.wallet;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherWalletRepository {

    void save(Wallet wallet);

    List<Voucher> findVoucherByCustomer(UUID customerId);

    void removeWallet(UUID walletId);

    List<Customer> findCustomerByVoucher(UUID voucherId);

    Optional<Wallet> findWallet(UUID walletId);

    List<Wallet> findAll();
}
