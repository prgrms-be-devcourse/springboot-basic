package com.programmers.voucher.domain.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.repository.VoucherRepository;

public class VoucherFactory {

    private final Console console;
    private final VoucherRepository voucherRepository;

    public VoucherFactory(Console console, VoucherRepository voucherRepository) {
        this.console = console;
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherEnum voucherEnum) {
        if (VoucherEnum.FIXED == voucherEnum) {
            return voucherRepository.save(console.createFixedVoucher());
        }

        if (VoucherEnum.PERCENT == voucherEnum) {
            return voucherRepository.save(console.createPercentVoucher());
        }
        throw new IllegalStateException("바우처 프로그램 내부적으로 문제가 생겼습니다. 문의 부탁드립니다.");
    }
}
