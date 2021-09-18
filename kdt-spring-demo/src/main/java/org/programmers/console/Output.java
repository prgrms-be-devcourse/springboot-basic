package org.programmers.console;

import org.programmers.customer.model.BlackListCustomer;
import org.programmers.customer.model.Customer;
import org.programmers.voucher.model.VoucherBase;
import org.programmers.voucher.model.Voucher;

import java.util.List;

public interface Output {
    void initInfoOutput();

    void showInputError();

    void exitOutput();

    void printBlackList(List<BlackListCustomer> blackListCustomerList);

    void printVoucherDataBaseList(List<VoucherBase> voucherBaseList);

    void printCustomerList(List<Customer> customerList);

    void printCustomer(Customer customer);

    void printFindVoucher(VoucherBase voucherBase);

    void customerInitInfoOutput();

    void voucherInitInfoOutput();

    void exitModeOutput();

    void voucherTypeInfoOutput(String mode);

    void voucherNumberInfoOutput(String mode);

    void customerNameInfoOutput(String mode);

    void customerEmailInfoOutput(String mode);

    void printVoucherFileList(List<Voucher> voucherList);
}
