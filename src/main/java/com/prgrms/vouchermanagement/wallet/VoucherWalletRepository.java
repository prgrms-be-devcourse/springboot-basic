package com.prgrms.vouchermanagement.wallet;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherWalletRepository {

    void save(Wallet wallet);

    List<Voucher> findVoucherByCustomer(Customer customer);

    void remove(UUID walletId);

    List<Customer> findCustomerByVoucher(Voucher voucher);
}
