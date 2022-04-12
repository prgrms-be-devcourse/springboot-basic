package com.waterfogsw.voucher.console;

import com.waterfogsw.voucher.exception.InvalidAmountException;
import com.waterfogsw.voucher.exception.InvalidMenuException;
import com.waterfogsw.voucher.exception.InvalidPercentException;
import com.waterfogsw.voucher.exception.InvalidVoucherTypeException;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

public interface Input {
    Menu inputMenu() throws InvalidMenuException;
    VoucherType inputVoucherType() throws InvalidVoucherTypeException;
    Double inputPercent() throws InvalidPercentException;
    Double inputAmount() throws InvalidAmountException;
}
