package com.programmers.wallet.controller;

import com.programmers.customer.controller.CustomerController;
import com.programmers.customer.domain.Customer;
import com.programmers.customer.dto.CustomerResponseDto;
import com.programmers.exception.InvalidRequestValueException;
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

    private static final String DELETE_ONE_VOUCHER_NUMBER = "1";
    private static final String DELETE_ALL_VOUCHERS_NUMBER = "2";

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
            case SEARCH_VOUCHER -> searchVoucherToGetCustomer();
            case DELETE_VOUCHER -> deleteVoucher();
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

    public void searchVoucherToGetCustomer() {
        console.printWalletSearchVoucherTitleMessage();
        voucherController.getVoucherList();

        console.printWalletSearchVoucherIdMessage();
        UUID voucherId = UUID.fromString(console.readInput());

        CustomerResponseDto customerResponseDto = walletService.findCustomerByVoucherId(voucherId);
        Customer customer = new Customer(customerResponseDto.id(), customerResponseDto.name());
        console.printCustomer(customer);
    }

    public void deleteVoucher() {
        console.printWalletDeleteVoucherTitleMessage();
        UUID customerId = getCustomerIdToDeleteVoucher();
        VouchersResponseDto vouchersResponseDto = walletService.findByCustomerId(customerId);

        console.printVoucherListTitle();
        voucherController.getVouchersResult(vouchersResponseDto.vouchers());

        selectDeleteType(customerId);
    }

    public UUID getCustomerIdToDeleteVoucher() {
        customerController.getNormalCustomerList();

        console.printWalletDeleteCustomerIdMessage();
        return UUID.fromString(console.readInput());
    }

    public void selectDeleteType(UUID customerId) {
        console.printDeleteTypeVoucherSelectionMessage();
        String command = console.readInput();
        checkDeleteTypeSelection(command);

        switch (command) {
            case DELETE_ONE_VOUCHER_NUMBER -> deleteOneVoucher(customerId);
            case DELETE_ALL_VOUCHERS_NUMBER -> deleteAllVouchers(customerId);
        }
    }

    private void checkDeleteTypeSelection(String deleteTypeRequest) {
        if (deleteTypeRequest.isEmpty()) {
            throw new InvalidRequestValueException("[ERROR] Delete Type 번호 요청 값이 비었습니다.");
        }

        if (!deleteTypeRequest.equals(DELETE_ONE_VOUCHER_NUMBER) && !deleteTypeRequest.equals(DELETE_ALL_VOUCHERS_NUMBER)) {
            throw new InvalidRequestValueException("[ERROR] 요청하신 Delete Type 번호가 유효하지 않습니다.");
        }
    }

    public void deleteOneVoucher(UUID customerId) {
        console.printDeleteVoucherIdMessage();
        UUID voucherId = UUID.fromString(console.readInput());

        WalletRequestDto walletRequestDto = new WalletRequestDto(voucherId, customerId);
        walletService.deleteByVoucherIdAndCustomerId(walletRequestDto);
        console.printDeleteVoucherCompleteMessage();
        log.info("The voucher of one customer has been deleted. customer id = {}", customerId);
    }

    public void deleteAllVouchers(UUID customerId) {
        walletService.deleteAllByCustomerId(customerId);
        console.printDeleteAllVouchersCompleteMessage();
        log.info("All vouchers of one customer have been deleted. customer id = {}", customerId);
    }
}