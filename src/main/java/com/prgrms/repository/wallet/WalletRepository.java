package com.prgrms.repository.wallet;

import com.prgrms.model.customer.Customer;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.wallet.Wallet;

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
