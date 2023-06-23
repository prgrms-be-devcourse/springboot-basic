package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.menu.Menu;
import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.model.voucher.VoucherDataAccessor;
import com.dev.voucherproject.view.Console;

import java.util.ArrayList;
import java.util.List;

public class ListMenuExecutor extends SelectMenuExecutor {
    public ListMenuExecutor(Menu menu, VoucherDataAccessor voucherDataAccessor, Console console) {
        super(menu, voucherDataAccessor, console);
    }

    @Override
    public void execute(Menu menu) {
        if (isSatisfiedBy(menu)) {
            List<Voucher> vouchers = voucherDataAccessor.findAll();
            List<VoucherDto> dtos = getVoucherDtos(vouchers);
            console.printAllVoucherDtos(dtos);
        }
    }

    private List<VoucherDto> getVoucherDtos(List<Voucher> vouchers) {
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
