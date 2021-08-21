package org.prgrms.kdt.voucher.io;

import org.prgrms.kdt.voucher.domain.Voucher;
import java.util.List;

public interface Output {
    void printInitText();
    void printExitText();
    void printCommandError(String command);
    void printSuccess();
    void printVoucherList(List<Voucher> voucherList);
}
