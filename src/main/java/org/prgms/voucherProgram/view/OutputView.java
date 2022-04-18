package org.prgms.voucherProgram.view;

import java.util.List;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.prgms.voucherProgram.dto.WalletVoucherDto;

public interface OutputView {

    void printVoucher(VoucherDto VoucherDto);

    void printVoucher(WalletVoucherDto walletVoucherDto);

    void printCustomer(CustomerDto customerDto);

    void printVouchers(List<VoucherDto> vouchers);

    void printWalletVouchers(List<WalletVoucherDto> vouchers);

    void printCustomers(List<CustomerDto> customers);

    void printBlackList(List<Customer> customers);

    void printSuccess();

    void printError(String message);
}
