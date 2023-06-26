package org.devcourse.springbasic.global.io.output.voucher;

import org.devcourse.springbasic.global.io.console.voucher.VoucherConsoleOutput;

import java.util.function.Supplier;

public enum VoucherOutputType {

    CONSOLE(VoucherConsoleOutput::new);

    private final Supplier<VoucherOutput> output;

    VoucherOutputType(Supplier<VoucherOutput> inputSupplier) {
        this.output = inputSupplier;
    }

    public VoucherOutput getOutput() {
        return output.get();
    }
}
