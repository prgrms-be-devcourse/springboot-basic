package com.waterfogsw.voucher;

import com.waterfogsw.voucher.member.MemberService;
import com.waterfogsw.voucher.voucher.Voucher;
import com.waterfogsw.voucher.voucher.VoucherService;
import com.waterfogsw.voucher.voucher.VoucherType;

import java.util.List;

public class ConsoleController implements ApplicationController {
    private final VoucherService voucherService;
    private final MemberService memberService;

    public ConsoleController(VoucherService voucherService, MemberService memberService) {
        this.voucherService = voucherService;
        this.memberService = memberService;
    }

    @Override
    public void createVoucher(VoucherType type, Double value) {

    }

    public List<Voucher> listVoucher() {
        return null;
    }

    public List<String> printBlackList() {
        return null;
    }
}
