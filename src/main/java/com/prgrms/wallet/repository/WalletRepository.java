package com.prgrms.wallet.repository;

import com.prgrms.custoemer.model.Customer;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.wallet.model.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository {

    Optional<Wallet> findById(int walletId);

    List<Wallet> findAllWallet();

    List<Customer> findAllCustomersByVoucher(int voucherId);

    Vouchers findAllVouchersByCustomer(int customerId);

    Wallet insert(Wallet wallet);

    Wallet deleteWithVoucherIdAndCustomerId(int voucherId, int customerId);

}
