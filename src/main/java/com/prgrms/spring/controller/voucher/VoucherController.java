package com.prgrms.spring.controller.voucher;

import com.prgrms.spring.domain.voucher.Voucher;
import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.service.voucher.VoucherService;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void createVoucher() {
        System.out.println("생성하고 싶은 바우처 타입을 숫자로 선택해주세요.");
        for (VoucherType vt: VoucherType.values()) {
            System.out.println(vt.getId() + ". " + vt.getName());
        }
        int select = 1;
        VoucherType type = VoucherType.matchType(select);
        System.out.println(type.getPromptMessage());
        Long discount = 80L;
        Voucher voucher = voucherService.createVoucher(type, discount);
        if (voucher == null) {
            System.out.println("바우처 등록에 실패하였습니다.");
            return;
        }
        System.out.println("정상적으로 바우처가 등록되었습니다.");
    }

    public void getAllVoucher() {
        voucherService.getAllVoucher().forEach(System.out::println);
    }
}
