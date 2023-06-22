package com.programmers.springweekly.controller;

import com.programmers.springweekly.domain.ProgramMenu;
import com.programmers.springweekly.domain.Voucher;
import com.programmers.springweekly.domain.VoucherMenu;
import com.programmers.springweekly.service.VoucherService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }

    public void createVoucher(){
        // 어떤 바우처를 생성할 것인지에 대한 Input값 받기

        voucherService.saveVoucher(VoucherMenu.FIXED, 100);
    }

    public void getVoucherList(){
        Optional<Map<UUID, Voucher>> voucherMap= voucherService.findVoucherAll();

        voucherMap.orElseThrow(() -> new NullPointerException("저장된 값이 없습니다"));

        // print
    }
}
