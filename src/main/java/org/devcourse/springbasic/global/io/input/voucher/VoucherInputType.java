package org.devcourse.springbasic.global.io.input.voucher;

import org.devcourse.springbasic.global.io.console.voucher.VoucherConsoleInput;

import java.util.function.Supplier;

public enum VoucherInputType {
    CONSOLE(VoucherConsoleInput::new);

    private final Supplier<VoucherInput> input;

    VoucherInputType(Supplier<VoucherInput> inputSupplier) {
        this.input = inputSupplier;
    }

    public VoucherInput getInput() {
        return input.get();
    }
}
