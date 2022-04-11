package org.prgrms.kdt.io;

// import java.util.List;
// import org.prgrms.kdt.blacklist.domain.Blacklist;
// import org.prgrms.kdt.voucher.domain.Voucher;

import org.prgrms.kdt.blacklist.domain.Blacklist;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.HashMap;
import java.util.UUID;

public interface Output {
    void init();

    void selectVoucherType();
    void setVoucherDiscount();

    void printVoucherList(HashMap<UUID, Voucher> voucherRepo);
    void emptyVoucherList();

    void printInvalidCmd(String msg);

    void printBlackList(HashMap<String, Blacklist> blacklistMap);

}
