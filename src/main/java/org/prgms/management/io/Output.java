package org.prgms.management.io;

import org.prgms.management.blacklist.vo.Blacklist;
import org.prgms.management.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Output {
    void init();

    void chooseVoucherType();

    void voucherCreateSuccess();

    void voucherCreateFail();

    void voucherList(List<Voucher> voucherList);

    void blackList(List<Blacklist> blackList);

    void delete(String msg);
}
