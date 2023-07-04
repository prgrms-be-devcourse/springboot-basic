package com.programmers.controller;

import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.VoucherType;
import com.programmers.domain.voucher.dto.VoucherCreateRequestDto;
import com.programmers.io.Console;
import com.programmers.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import static com.programmers.util.ValueFormatter.changeDiscountValueToNumber;
import static com.programmers.util.ValueFormatter.reformatVoucherType;

@Controller
public class VoucherController {

    private static final Logger log = LoggerFactory.getLogger(VoucherController.class);

    private final Console console;
    private final VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public Voucher createVoucher() {
        Voucher voucher = makeVoucher();
        voucherService.save(new VoucherCreateRequestDto(voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType()));
        console.printVoucherCreated();
        log.info("The voucher has been created.");

        return voucher;
    }

    public Voucher makeVoucher() {
        console.printVoucherType();
        String voucherTypeInput = reformatVoucherType(console.readInput());

        console.printDiscountValueInput();
        Long discountValue = changeDiscountValueToNumber(console.readInput());

        console.printVoucherNameInput();
        String voucherName = console.readInput();

        return VoucherType.createVoucher(voucherTypeInput, voucherName, discountValue);
    }
}
