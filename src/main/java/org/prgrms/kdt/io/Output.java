package org.prgrms.kdt.io;

import java.util.Map;
import java.util.UUID;
import org.prgrms.kdt.voucher.Voucher;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 11:31 오후
 */
public interface Output {

    void guide();

    void successCreate();

    void printVoucherChoice();

    void commandError();

    void printVouchers(Map<UUID, Voucher> vouchers);

    void printNextCommand();

    void printExit();
}
