package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.storage.VoucherStorage;
import com.dev.voucherproject.view.Console;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ListMenuController extends MenuUsingConsoleAndStorage {
    public ListMenuController(VoucherStorage voucherStorage, Console console) {
        super(voucherStorage, console);
    }

    @Override
    public void execute() {
        List<Voucher> vouchers = voucherStorage.findAll();
        List<VoucherDto> dtos = createVoucherDtos(vouchers);
        console.printAllVouchers(dtos);
    }

    private List<VoucherDto> createVoucherDtos(List<Voucher> vouchers) {
        List<VoucherDto> dtos = new ArrayList<>();

        for (Voucher voucher : vouchers) {
            if (voucher instanceof FixedAmountVoucher) {
                dtos.add(VoucherDto.fromVoucher(VoucherPolicy.FIXED_AMOUNT_VOUCHER, voucher));
            } else if (voucher instanceof PercentDiscountVoucher) {
                dtos.add(VoucherDto.fromVoucher(VoucherPolicy.PERCENT_DISCOUNT_VOUCHER, voucher));
            }
        }

        return dtos;
    }
}
