package org.prgrms.kdt.common.io;

import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.voucher.model.Voucher;
import java.util.List;

public interface Output {
    void printInitText();
    void printExitText();
    void printCommandError(String command);
    void printSuccess();
    void printVoucherList(List<Voucher> voucherList);
    void printBlackList(List<Customer> customerList);
}
