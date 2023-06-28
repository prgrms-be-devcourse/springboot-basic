package com.wonu606.Controller.command;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.io.ConsolePrinterView;
import com.wonu606.vouchermanager.service.VoucherService;
import java.util.List;

public class VoucherListCommand implements VoucherCommand{

    private final ConsolePrinterView printerView;
    private final VoucherService voucherService;

    public VoucherListCommand(ConsolePrinterView printerView, VoucherService voucherService) {
        this.printerView = printerView;
        this.voucherService = voucherService;
    }

    @Override
    public CommandResult execute() {
        List<Voucher> voucherList = voucherService.getVoucherList();
        printerView.printVoucherList(voucherList);
        return new CommandResult();
    }
}
