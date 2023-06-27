package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.model.storage.voucher.VoucherStorage;
import com.dev.voucherproject.view.Console;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ListMenuController extends MenuUsingConsoleAndStorage {
    public ListMenuController(VoucherStorage voucherStorage, Console console) {
        super(voucherStorage, console);
    }

    @Override
    public void execute() {
        List<Voucher> vouchers = voucherStorage.findAll();

        List<VoucherDto> dtos = vouchers.stream()
                .map(VoucherDto::fromVoucher)
                .toList();

        console.printAllVouchers(dtos);
    }
}
