package com.wonu606.vouchermanager.Controller.command;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.io.ConsolePrinterView;
import com.wonu606.vouchermanager.service.VoucherService;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VoucherListCommand implements VoucherCommand {

    private final ConsolePrinterView printerView;
    private final VoucherService voucherService;

    @Override
    public CommandResult execute() {
        List<Voucher> voucherList = voucherService.getVoucherList();
        printerView.printVoucherList(voucherList);
        return new CommandResult();
    }
}
