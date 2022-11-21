package com.programmers.wallet.controller;

import com.programmers.customer.Customer;
import com.programmers.view.View;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.wallet.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.programmers.message.Message.VOUCHER_CUSTOMER_ID;
import static com.programmers.message.Message.VOUCHER_ID;

@Component
public class WalletController {
    private final WalletService walletService;
    private final View view;

    public WalletController(WalletService walletService, View view) {
        this.walletService = walletService;
        this.view = view;
    }

    public void assign() {
        view.printMessage(VOUCHER_CUSTOMER_ID);
        String customerId = view.getUserCommand();
        UUID customerUUID = UUID.fromString(customerId);

        view.printMessage(VOUCHER_ID);
        String voucherId = view.getUserCommand();
        UUID voucherUUID = UUID.fromString(voucherId);

        walletService.assignVoucher(customerUUID, voucherUUID);
    }

    public void showCustomerVouchers() {
        view.printMessage(VOUCHER_CUSTOMER_ID);
        String customerId = view.getUserCommand();
        UUID customerUUID = UUID.fromString(customerId);

        List<Voucher> vouchers = walletService.searchVouchersByCustomerId(customerUUID);

        for (Voucher voucher : vouchers) {
            view.printVoucher(voucher);
        }
    }

    public void findVoucherOwner() {
        view.printMessage(VOUCHER_ID);
        String voucherId = view.getUserCommand();
        UUID voucherUUID = UUID.fromString(voucherId);

        Customer customer = walletService.searchCustomerByVoucherId(voucherUUID);
        view.printCustomer(customer);
    }

    public void delete() {
        view.printMessage(VOUCHER_CUSTOMER_ID);
        String customerId = view.getUserCommand();
        UUID customerUUID = UUID.fromString(customerId);

        view.printMessage(VOUCHER_ID);
        String voucherId = view.getUserCommand();
        UUID voucherUUID = UUID.fromString(voucherId);

        walletService.removeCustomerVoucher(customerUUID, voucherUUID);
    }
}
