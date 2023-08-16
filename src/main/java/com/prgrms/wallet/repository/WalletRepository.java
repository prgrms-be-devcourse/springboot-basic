package com.prgrms.wallet.repository;

import com.prgrms.custoemer.model.Customer;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.wallet.model.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository {

    Optional<Wallet> findById(String walletId);

    List<Wallet> findAllWallet();

    List<Customer> findAllCustomersByVoucher(String voucherId);

    Vouchers findAllVouchersByCustomer(String customerId);

    Wallet insert(Wallet wallet);

    Wallet deleteWithVoucherIdAndCustomerId(String voucherId, String customerId);

}
