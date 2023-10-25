package com.pgms.part1.domain.wallet.controller;

import com.pgms.part1.domain.customer.service.CustomerService;
import com.pgms.part1.domain.voucher.service.VoucherService;
import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.domain.wallet.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WalletController {

    private final WalletService walletService;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public WalletController(WalletService walletService, VoucherService voucherService, CustomerService customerService) {
        this.walletService = walletService;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void addWallet(WalletCreateRequestDto walletCreateRequestDto){
        walletService.addWallet(walletCreateRequestDto);
    }

    public void deleteWallet(Long walletId){
        walletService.deleteWallet(walletId);
    }

    public void listVouchersByCustomer(Long customerId){
        List<Wallet> wallets = walletService.listWalletsByCustomer(customerId);
        voucherService.listVouchersByWallets(wallets);
    }

    public void listCustomersByVoucher(Long voucherId){
        List<Wallet> wallets = walletService.listWalletsByVoucher(voucherId);
        customerService.listCustomersByWallets(wallets);
    }

}
