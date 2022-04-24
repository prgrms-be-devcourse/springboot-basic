package org.programs.kdt.IO;

import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Wallet.Wallet;

import java.util.List;

public interface Output {
    void printVoucherList(List<Voucher> voucherList);
    void output(String s);
    void printCustomerList(List<Customer> customerList);
    void printCustomer(Customer customer);
    void printVoucher(Voucher voucher);
    void printWallet(Wallet wallet);
    void printWalletList(List<Wallet> walletList);
    void printError(ErrorCode errorCode);
}
