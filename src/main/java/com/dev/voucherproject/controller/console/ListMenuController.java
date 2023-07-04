package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.model.storage.voucher.VoucherStorage;
import com.dev.voucherproject.view.Console;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ListMenuController implements MenuController {
    private final Console console;
    private final VoucherStorage voucherStorage;

    public ListMenuController(Console console, VoucherStorage voucherStorage) {
        this.console = console;
        this.voucherStorage = voucherStorage;
    }

    @Override
    public void execute() {
        List<Voucher> vouchers = voucherStorage.findAll();

        List<VoucherDto> dtos = vouchers.stream()
                .map(Voucher::conversionDto)
                .toList();

        console.printAllVouchers(dtos);
    }
}
