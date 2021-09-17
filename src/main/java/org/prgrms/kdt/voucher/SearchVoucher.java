package org.prgrms.kdt.voucher;

import java.time.LocalDate;

/**
 * Created by yhh1056
 * Date: 2021/09/16 Time: 1:25 오후
 */
public class SearchVoucher {

    private VoucherType voucherType;
    private LocalDate beforeDate;
    private LocalDate afterDate;

    public SearchVoucher(VoucherType voucherType, LocalDate beforeDate, LocalDate afterDate) {
        this.voucherType = voucherType;
        this.beforeDate = beforeDate;
        this.afterDate = afterDate;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDate getBeforeDate() {
        return beforeDate;
    }

    public LocalDate getAfterDate() {
        return afterDate;
    }
}
