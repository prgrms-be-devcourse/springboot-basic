package org.prgms.management.io;

import org.prgms.management.entity.Voucher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public interface Output {
    void init();
    void chooseVoucherType();
    void voucherCreateSuccess();
    void voucherCreateFail();
    void voucherList(Map<UUID, Voucher> voucherList);
    void blackList(Map<UUID, String> blackList);
}
