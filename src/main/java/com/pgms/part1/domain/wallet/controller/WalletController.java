package com.pgms.part1.domain.wallet.controller;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.service.CustomerService;
import com.pgms.part1.domain.voucher.dto.VoucherWalletResponseDtos;
import com.pgms.part1.domain.voucher.service.VoucherService;
import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import com.pgms.part1.domain.wallet.service.WalletService;
import com.pgms.part1.view.WalletConsoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WalletController {

    private final Logger log = LoggerFactory.getLogger(WalletController.class);
    private final WalletService walletService;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletConsoleView walletConsoleView;

    public WalletController(WalletService walletService, VoucherService voucherService, CustomerService customerService, WalletConsoleView walletConsoleView) {
        this.walletService = walletService;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletConsoleView = walletConsoleView;
    }

    public void getMenu() {
        String command = walletConsoleView.getMenu();

        switch (command) {
            case "add" -> addWallet();
            case "customer list" -> listCustomersByVoucher();
            case "voucher list" -> listVouchersByCustomer();
            case "delete" -> deleteWallet();
            case "exit" -> {return;}
            default -> {
                walletConsoleView.error(new RuntimeException("Please Enter Again!!"));
                log.warn("Invalid Menu Command Input");
            }
        }

        getMenu();
    }

    public void addWallet(){
        WalletCreateRequestDto walletCreateRequestDto = walletConsoleView.addWallet();
        walletService.addWallet(walletCreateRequestDto);
    }

    public void deleteWallet(){
        Long id = walletConsoleView.deleteWallet();
        walletService.deleteWallet(id);
    }

    public void listVouchersByCustomer() {
        try {
            Long id = walletConsoleView.getCustomerId();
            List<VoucherWalletResponseDtos> voucherWalletResponseDtos = walletService.listVouchersByCustomer(id);
            walletConsoleView.listVoucher(voucherWalletResponseDtos);
        } catch (Exception e) {
            walletConsoleView.error(e);
        }
    }

    public void listCustomersByVoucher(){
        try{
            Long id = walletConsoleView.getVoucherId();
            List<CustomerResponseDto> customerResponseDtos = walletService.listCustomersByVoucher(id);
            walletConsoleView.listCustomers(customerResponseDtos);
        } catch (Exception e){
            walletConsoleView.error(e);
        }
    }
}
