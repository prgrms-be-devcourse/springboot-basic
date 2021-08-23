package org.prgrms.kdt.io;

import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;
import java.util.List;

public interface Output {
    void printInitText();
    void printExitText();
    void printCommandError(String command);
    void printSuccess();
    void printVoucherList(List<Voucher> voucherList);
    void printBlackList(List<Customer> customerList);
}
