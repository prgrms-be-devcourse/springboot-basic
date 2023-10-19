package com.programmers.vouchermanagement.voucher;

import com.programmers.vouchermanagement.consolecomponent.ConsoleManager;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Controller
public class VoucherController {
    private final ConsoleManager consoleManager;
    private final VoucherService voucherService;

    public VoucherController(ConsoleManager consoleManager, VoucherService voucherService) {
        this.consoleManager = consoleManager;
        this.voucherService = voucherService;
    }

    //TODO: add DTO
    public Voucher create(CreateVoucherRequestDTO createVoucherRequestDTO) {
        return voucherService.create(createVoucherRequestDTO);
    }

    public List<Voucher> readAllVouchers() {
        try {
            return voucherService.readAllVouchers();
        } catch (RuntimeException e) {
            //TODO: add exception handling method externally
            consoleManager.printException(e);

            //TODO: reconsider return empty list from service
            return Collections.emptyList();
        }
    }
}
