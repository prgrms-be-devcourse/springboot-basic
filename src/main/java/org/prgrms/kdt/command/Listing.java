package org.prgrms.kdt.command;

import org.prgrms.kdt.exception.InvalidIOMessageException;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.VoucherService;

public class Listing implements Command{
    private final Input inputStream;
    private final Output outputStream;
    private final VoucherService voucherService;

    public Listing(Input inputStream, Output outputStream, VoucherService voucherService){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.voucherService=voucherService;
    }

    @Override
    public void doCommands() throws InvalidIOMessageException {
        var vouchers = voucherService.getAllVouchers();

        if (vouchers.size() == 0) {
            outputStream.write("There are not any vouchers\n\n");
            return;
        }

        for (var voucher : vouchers) {
            outputStream.write(voucher.toString() + "\n");
        }
        outputStream.write("\n");
    }
}
