package com.prgrms.voucher.vo;

import com.prgrms.voucher.model.voucher.Voucher;
import java.util.List;

public record Vouchers(List<Voucher> vouchers) {

    public boolean isEmpty(List<Voucher> vouchers) {
        return vouchers.isEmpty();
    }

    @Override
    public List<Voucher> vouchers() {
        return vouchers.stream().toList();
    }

}
