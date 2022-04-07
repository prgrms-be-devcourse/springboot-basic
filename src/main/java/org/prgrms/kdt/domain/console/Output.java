package org.prgrms.kdt.domain.console;

import org.prgrms.kdt.domain.voucher.model.Voucher;

import java.util.List;

public interface Output {

    void printExit();

    void printAllVouchers(List<Voucher> vouchers);

    void printWrongCommandError();
}
