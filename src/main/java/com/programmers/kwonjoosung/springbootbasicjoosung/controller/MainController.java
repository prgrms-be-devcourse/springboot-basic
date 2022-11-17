package com.programmers.kwonjoosung.springbootbasicjoosung.controller;

import com.programmers.kwonjoosung.springbootbasicjoosung.console.Console;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.CustomerService;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.VoucherService;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MainController {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;
    private final Console console;

    MainController(VoucherService voucherService, CustomerService customerService, WalletService walletService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
        this.console = console;
    }

    public void selectFunction(CommandType commandType) {
        switch (commandType) {
            case CREATE -> createVoucher();
            case LIST -> showVoucherList();
            case BLACKLIST -> showCustomerBlackList();
            case SELECT -> selectVoucher();
            case UPDATE -> updateVoucher();
            case DELETE -> deleteVoucher();
            case WALLET -> showWallet();
            case ASSIGN -> insertWallet();

        }
    }

    private void insertWallet() {
        UUID customerId = UUID.fromString(console.inputCustomId());
        UUID voucherId = UUID.fromString(console.inputVoucherId());
        walletService.assignVoucherToCustomer(customerId, voucherId);
    }

    private void showWallet() {
        UUID customerId = UUID.fromString(console.inputCustomId());
        walletService.findAllVouchersFromWallet(customerId)
                .forEach(console::show);
    }

    private void selectVoucher() {
        UUID voucherId = UUID.fromString(console.inputVoucherId());
        Voucher selectedVoucher = voucherService.findVoucher(voucherId);
        console.show(selectedVoucher);
    }

    private void updateVoucher() {
        Voucher oldVoucher = VoucherFactory.createVoucher(
                VoucherType.getVoucherType(console.inputVoucherType()),
                UUID.fromString(console.inputCustomId()),
                console.inputDiscount());
        Voucher newVoucher = voucherService.updateVoucher(oldVoucher);
        console.show(newVoucher);
    }

    private void deleteVoucher() {
        UUID voucherId = UUID.fromString(console.inputCustomId());
        voucherService.deleteVoucher(voucherId);
    }

    private void createVoucher() {  // controller에서 담당해도 되는 로직 인지 궁긍합니다.?
        VoucherType voucherType = VoucherType.getVoucherType(console.inputVoucherType());
        long discount = console.inputDiscount();
        Voucher voucher = voucherService.createVoucher(voucherType, discount);
        console.show(voucher);
    }

    private void showVoucherList() {
        voucherService.getAllVouchers().forEach(console::show);
    }

    private void showCustomerBlackList() {
        customerService.getBlackList().forEach(console::show);
    }

}
