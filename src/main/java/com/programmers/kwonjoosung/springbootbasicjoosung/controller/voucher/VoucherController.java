package com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.console.Console;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.Command;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.VoucherService;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.kwonjoosung.springbootbasicjoosung.console.message.VoucherResponseMessage.*;

@Component
public class VoucherController {
    private final VoucherService voucherService;
    private final WalletService walletService;
    private final Console console;

    public VoucherController(VoucherService voucherService, WalletService walletService, Console console) {
        this.voucherService = voucherService;
        this.walletService = walletService;
        this.console = console;
    }

    public void execute(Command command) {
        switch (command) {
            case CREATE -> createVoucher();
            case READ -> findVoucher();
            case UPDATE -> updateVoucher();
            case DELETE -> deleteVoucher();
            case FIND -> findOwnedCustomer();
            case LIST -> listVoucher();
        }
    }

    private void findOwnedCustomer() {
        UUID voucherId = console.getVoucherId();
        Optional<Customer> customer = walletService.findCustomerByVoucherId(voucherId);
        customer.ifPresentOrElse(console::printCustomer, () -> console.printMessage(NOT_FOUND_OWNED_CUSTOMER));
    }

    private void createVoucher() {
        VoucherType voucherType = VoucherType.of(console.getVoucherType());
        long discount = console.getDiscount();
        Optional<Voucher> voucher = voucherService.createVoucher(voucherType,discount);
        voucher.ifPresentOrElse(console::printVoucher, () -> console.printMessage(CREATE_VOUCHER_FAIL));
    }

    private void findVoucher() {
        UUID voucherId = console.getVoucherId();
        Optional<Voucher> voucher = voucherService.findVoucher(voucherId);
        voucher.ifPresentOrElse(console::printVoucher, () -> console.printError(NOT_FOUND_VOUCHER_ERROR));
    }

    private void listVoucher() {
        List<Voucher> allVouchers = voucherService.getAllVouchers();
        if(allVouchers.isEmpty()) console.printError(NOT_YET_VOUCHER_SAVE_ERROR);
        allVouchers.forEach(console::printVoucher);
    }

    private void updateVoucher() {
        UUID voucherId = console.getVoucherId();
        VoucherType voucherType = VoucherType.of(console.getVoucherType());
        long discount = console.getDiscount();
        boolean result = voucherService.updateVoucher(voucherId, voucherType, discount);
        if(result) console.printMessage(UPDATE_VOUCHER_SUCCESS);
        else console.printError(NOT_FOUND_VOUCHER_ERROR);
    }

    private void deleteVoucher() {
        UUID voucherId = console.getVoucherId();
        boolean result = voucherService.deleteVoucher(voucherId);
        if(result) console.printMessage(DELETE_VOUCHER_SUCCESS);
        else console.printError(NOT_FOUND_VOUCHER_ERROR);
    }

}
