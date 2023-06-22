package com.programmers.voucher.domain.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.stream.VoucherStream;

public class VoucherFactory {

    private final Console console;
    private final VoucherStream voucherStream;

    public VoucherFactory(Console console, VoucherStream voucherStream) {
        this.console = console;
        this.voucherStream = voucherStream;
    }

    public Voucher createVoucher(VoucherEnum voucherEnum) {
        if (VoucherEnum.FIXED == voucherEnum) {
            return voucherStream.save(console.createFixedVoucher());
        }

        if (VoucherEnum.PERCENT == voucherEnum) {
            return voucherStream.save(console.createPercentVoucher());
        }
        throw new IllegalStateException("바우처 프로그램 내부적으로 문제가 생겼습니다. 문의 부탁드립니다.");
    }
}
