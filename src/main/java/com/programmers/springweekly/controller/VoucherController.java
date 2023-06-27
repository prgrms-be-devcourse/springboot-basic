package com.programmers.springweekly.controller;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.service.VoucherService;
import com.programmers.springweekly.view.Console;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
@Slf4j
public class VoucherController {

    private final VoucherService voucherService;
    private final Console console;

    public void createVoucher() {
        console.outputSelectCreateVoucherGuide();

        VoucherType voucherType = VoucherType.findVoucherMenu(console.inputMessage());

        console.outputDiscountGuide();
        String inputNumber = console.inputMessage();
        log.info("user input: {} ", inputNumber);

        voucherService.saveVoucher(voucherType, inputNumber);
    }

    public Map<UUID, Voucher> getVoucherList() {
        return voucherService.findVoucherAll();
    }
}
