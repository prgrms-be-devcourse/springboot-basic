package org.prgrms.kdt.io;

// import java.util.List;
// import org.prgrms.kdt.blacklist.domain.Blacklist;
// import org.prgrms.kdt.voucher.domain.Voucher;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.HashMap;

public interface Output {
    void init();
    void selectVoucherType();
    void setVoucherDiscount();
    void printVoucherList(HashMap<String, Voucher> voucherRepository);
    void emptyVoucherList();
    void printInvalidCmd(String msg);

    // void printBlackList(List<Blacklist> blackList);
    // void printVoucherList(List<Voucher> voucherList);
}
