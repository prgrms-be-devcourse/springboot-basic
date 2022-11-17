package com.programmers.wallet.controller;

import com.programmers.customer.Customer;
import com.programmers.view.View;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.wallet.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {
    private final WalletService walletService;
    private final View view;

    public WalletController(WalletService walletService, View view) {
        this.walletService = walletService;
        this.view = view;
    }

    public void assign() {
        view.printMessage("바우처를 할당할 고객 ID를 입력해주세요.");
        String customerId = view.getUserCommand();
        UUID customerUUID = UUID.fromString(customerId);

        view.printMessage("할당할 바우처 ID를 입력해주세요.");
        String voucherId = view.getUserCommand();
        UUID voucherUUID = UUID.fromString(voucherId);

        walletService.assignVoucher(customerUUID, voucherUUID);
    }

    public void showCustomerVoucher() {
        view.printMessage("바우처를 조회할 고객 ID를 입력해주세요.");
        String customerId = view.getUserCommand();
        UUID customerUUID = UUID.fromString(customerId);

        List<Voucher> vouchers = walletService.searchVouchersByCustomerId(customerUUID);

        for (Voucher voucher : vouchers) {
            view.printVoucher(voucher);
        }
    }

    public void findVoucherOwner() {
        view.printMessage("소유자를 조회할 바우처 ID를 입력해주세요.");
        String voucherId = view.getUserCommand();
        UUID voucherUUID = UUID.fromString(voucherId);

        Customer customer = walletService.searchCustomerByVoucherId(voucherUUID);
        view.printCustomer(customer);
    }

    public void delete() {
        view.printMessage("바우처를 관리할 고객 ID를 입력해주세요.");
        String customerId = view.getUserCommand();
        UUID customerUUID = UUID.fromString(customerId);

        view.printMessage("삭제할 바우처의 ID를 입력해주세요.");
        String voucherId = view.getUserCommand();
        UUID voucherUUID = UUID.fromString(voucherId);

        walletService.removeCustomerVoucher(customerUUID, voucherUUID);
    }
}
