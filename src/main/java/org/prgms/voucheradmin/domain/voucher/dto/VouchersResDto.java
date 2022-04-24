package org.prgms.voucheradmin.domain.voucher.dto;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;

import java.util.List;

public class VouchersResDto {
    private long total;
    private List<Voucher> vouchers;

    public VouchersResDto(long total, List<Voucher> vouchers) {
        this.total = total;
        this.vouchers = vouchers;
    }

    public long getTotal() {
        return total;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }
}
