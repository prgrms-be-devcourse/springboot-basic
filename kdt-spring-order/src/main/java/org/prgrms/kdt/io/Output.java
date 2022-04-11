package org.prgrms.kdt.io;

// import java.util.List;
// import org.prgrms.kdt.blacklist.domain.Blacklist;
// import org.prgrms.kdt.voucher.domain.Voucher;

public interface Output {
    void init();
    void printInvalidCmd(String msg);
    void selectVoucherType();
    void selectDiscount();
    void printInvalidNum(String msg);
    void printInvalidVoucherType(String msg);

    // void printBlackList(List<Blacklist> blackList);
    // void printVoucherList(List<Voucher> voucherList);
}
