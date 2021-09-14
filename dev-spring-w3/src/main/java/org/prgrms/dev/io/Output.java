package org.prgrms.dev.io;

import org.prgrms.dev.blacklist.domain.Blacklist;
import org.prgrms.dev.voucher.domain.Voucher;

import java.util.List;

public interface Output {
    void init();

    void selectVoucherType();

    void printInvalidNumber();

    void printInvalidCommandType();

    void printInvalidVoucherType();

    void printBlackList(List<Blacklist> blackList);

    void printVoucherList(List<Voucher> voucherList);
}
