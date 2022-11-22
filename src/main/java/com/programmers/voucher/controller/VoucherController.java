package com.programmers.voucher.controller;

import com.programmers.view.View;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.programmers.message.Message.*;

@Component
public class VoucherController {
    private final VoucherService voucherService;
    private final View view;

    public VoucherController(VoucherService voucherService, View view) {
        this.voucherService = voucherService;
        this.view = view;
    }

    public void showCustomerVouchers() {
        view.printMessage(VOUCHER_CUSTOMER_ID);
        String customerId = view.getUserCommand();
        UUID customerUUID = UUID.fromString(customerId);

        List<Voucher> vouchers = voucherService.searchVouchersByCustomerId(customerUUID);

        for (Voucher voucher : vouchers) {
            view.printVoucher(voucher);
        }
    }

    public void showVoucherList() {
        List<Voucher> vouchers = voucherService.findAll();

        view.printList(vouchers);
    }

    public void createVoucher() {
        view.printMessage(VOUCHER_TYPE_MESSAGE);
        String voucherTypeInput = view.getUserCommand();

        view.printMessage(VOUCHER_VALUE_MESSAGE);
        String value = view.getUserCommand();

        voucherService.register(voucherTypeInput, value);
        view.printMessage(VOUCHER_CREATE_SUCCESS);

    }

    public void findVoucher() {
        view.printMessage(VOUCHER_ID);
        String userCommand = view.getUserCommand();
        UUID voucherId = UUID.fromString(userCommand);

        Voucher voucher = voucherService.getVoucher(voucherId);
        view.printVoucher(voucher);
    }
}
