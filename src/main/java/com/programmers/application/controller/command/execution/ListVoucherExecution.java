package com.programmers.application.controller.command.execution;

import com.programmers.application.dto.reponse.VoucherInfoResponse;
import com.programmers.application.io.IO;
import com.programmers.application.service.VoucherService;

import java.io.IOException;
import java.util.List;

public class ListVoucherExecution implements VoucherExecution {
    private final VoucherService voucherService;
    private final IO io;

    public ListVoucherExecution(VoucherService voucherService, IO io) {
        this.voucherService = voucherService;
        this.io = io;
    }

    @Override
    public void run() throws IOException {
        List<VoucherInfoResponse> voucherList = voucherService.findVoucherList();
        for (VoucherInfoResponse voucherInfoResponse : voucherList) {
            io.write(voucherInfoResponse.toString());
        }
    }
}
