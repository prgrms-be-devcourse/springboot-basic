package com.programmers.springweekly;

import com.programmers.springweekly.controller.VoucherController;
import com.programmers.springweekly.domain.VoucherMenu;
import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.view.Console;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsoleVoucher {

    private final VoucherController voucherController;
    private final Console console;

    public void run() {
        console.outputVoucherMenu();
        VoucherMenu voucherMenu = VoucherMenu.from(console.inputMessage());

        switch (voucherMenu) {
            case CREATE -> createVoucher();
            case SELECT -> getVoucherList();
            default -> throw new IllegalArgumentException("Input :" + voucherMenu + "찾으시는 바우처 메뉴가 없습니다.");
        }
    }

    private void createVoucher() {
        console.outputSelectCreateVoucherGuide();
        VoucherType voucherType = VoucherType.from(console.inputMessage());

        console.outputDiscountGuide();
        String inputNumber = console.inputMessage();
        log.info("user input: {} ", inputNumber);

        voucherController.createVoucher(voucherType, inputNumber);
    }

    private void getVoucherList() {
        Map<UUID, Voucher> voucherMap = voucherController.getVoucherList();

        if (voucherMap.isEmpty()) {
            console.outputErrorMessage("저장된 바우처가 없습니다.");
            return;
        }

        console.outputGetVoucherAll(voucherMap);
    }
}
