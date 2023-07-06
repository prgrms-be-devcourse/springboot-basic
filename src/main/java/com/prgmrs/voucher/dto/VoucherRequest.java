package com.prgmrs.voucher.dto;

import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.enums.ConsoleViewVoucherCreationEnum;

public class VoucherRequest {
    private final ConsoleViewVoucherCreationEnum consoleViewVoucherCreationEnum;
    private final DiscountValue discountValue;

    public VoucherRequest(ConsoleViewVoucherCreationEnum consoleViewVoucherCreationEnum, DiscountValue discountValue) {
        this.consoleViewVoucherCreationEnum = consoleViewVoucherCreationEnum;
        this.discountValue = discountValue;
    }

    public ConsoleViewVoucherCreationEnum getConsoleViewVoucherCreationEnum() {
        return consoleViewVoucherCreationEnum;
    }

    public DiscountValue getValue() {
        return discountValue;
    }
}
