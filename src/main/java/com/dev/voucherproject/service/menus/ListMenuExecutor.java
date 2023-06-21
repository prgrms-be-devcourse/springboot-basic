package com.dev.voucherproject.service.menus;

import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.service.VoucherService;
import com.dev.voucherproject.view.Console;

import java.util.ArrayList;
import java.util.List;

public class ListMenuExecutor extends SelectMenuExecutor {
    public ListMenuExecutor(Menu menu, VoucherService voucherService, Console console) {
        super(menu, voucherService, console);
    }

    @Override
    public void execute(Menu menu) {
        if (isSatisfiedBy(menu)) {
            List<Voucher> vouchers = voucherService.findAll();
            List<VoucherDto> dtos = getVoucherDtos(vouchers);
            console.printAllVoucherDtos(dtos);
        }
    }

    private static List<VoucherDto> getVoucherDtos(List<Voucher> vouchers) {
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
