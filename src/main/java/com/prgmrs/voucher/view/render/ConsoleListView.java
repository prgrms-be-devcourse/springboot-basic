package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.VoucherListResponse;
import com.prgmrs.voucher.enums.ListType;
import com.prgmrs.voucher.view.writer.ConsoleListWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleListView {
    private final VoucherController voucherController;
    private final ConsoleListWriter consoleListWriter;

    public ConsoleListView(VoucherController voucherController, ConsoleListWriter consoleListWriter) {
        this.voucherController = voucherController;
        this.consoleListWriter = consoleListWriter;
    }

    public void selectUser(ListType listType) {
        String name = "";
        VoucherListResponse voucherListResponse = voucherController.findByUsername(name);
        consoleListWriter.showList(voucherListResponse);
    }
}
