package com.programmers.wallet.controller;

import com.programmers.customer.controller.CustomerController;
import com.programmers.io.Console;
import com.programmers.voucher.controller.VoucherController;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.wallet.domain.WalletMenu;
import com.programmers.wallet.dto.WalletRequestDto;
import com.programmers.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private static final Logger log = LoggerFactory.getLogger(WalletController.class);

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletService walletService;

    public WalletController(Console console, VoucherController voucherController, CustomerController customerController, WalletService walletService) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletService = walletService;
    }

    public void activate() {
        console.printWalletMessage();
        String command = console.readInput();
        WalletMenu walletMenu = WalletMenu.findWalletMenu(command);

        switch (walletMenu) {
            case ASSIGN_VOUCHER -> assignVoucher();
            case SEARCH_CUSTOMER -> searchCustomerToGetVouchers();
            //  case SEARCH_VOUCHER -> searchVoucherToGetCustomer();
            //  case DELETE_VOUCHER -> deleteVoucher();
        }
    }

    public void assignVoucher() {
        console.printWalletAssignTitleMessage();
        voucherController.getVoucherList();
        customerController.getNormalCustomerList();

        WalletRequestDto walletRequestDto = makeWalletRequestDto();
        walletService.updateCustomerId(walletRequestDto);

        console.printWalletAssignCompleteMessage();
        log.info("The voucher assigned successfully. voucher id = {}, customer id = {}", walletRequestDto.voucherId(), walletRequestDto.customerId());
    }

    private WalletRequestDto makeWalletRequestDto() {
        console.printWalletAssignVoucherIdMessage();
        UUID voucherId = UUID.fromString(console.readInput());

        console.printWalletAssignCustomerIdMessage();
        UUID customerId = UUID.fromString(console.readInput());

        return new WalletRequestDto(voucherId, customerId);
    }

    public List<Voucher> searchCustomerToGetVouchers() {
        console.printWalletSearchCustomerTitleMessage();
        customerController.getNormalCustomerList();

        console.printWalletSearchCustomerIdMessage();
        UUID customerId = UUID.fromString(console.readInput());
        VouchersResponseDto vouchersResponseDto = walletService.findByCustomerId(customerId);

        console.printVoucherListTitle();
        return voucherController.getVouchersResult(vouchersResponseDto.vouchers());
    }
}