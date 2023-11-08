package org.prgrms.kdt.io;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.wallet.Wallet;

import java.util.List;
import java.util.Optional;

public interface OutputHandler {

    void outputString(String message);

    void outputCustomer(Customer customer);

    void outputVoucher(Voucher voucher);

    void outputWallet(Optional<Wallet> wallet);

    void outputWallets(List<Wallet> walletList);

    void outputVouchers(List<Voucher> voucherList);

    void outputBlackList(List<Customer> customerList);
}
