package com.prgrms.springbootbasic.view;

import com.prgrms.springbootbasic.domain.Voucher;
import java.util.Map;
import java.util.UUID;

public interface Output {

    void printMessage(String message);

    void printlnVoucherList(Map<UUID, Voucher> voucherMap);
}
