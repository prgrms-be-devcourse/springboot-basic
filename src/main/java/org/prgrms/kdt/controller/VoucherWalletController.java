package org.prgrms.kdt.controller;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherList;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.VoucherWalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VoucherWalletController {

    private final VoucherWalletService voucherWalletService;

    private final VoucherService voucherService;

    public VoucherWalletController(VoucherWalletService voucherWalletService, VoucherService voucherService) {
        this.voucherWalletService = voucherWalletService;
        this.voucherService = voucherService;
    }

    @GetMapping("/voucherWallet")
    public String showVoucherList(Model model) {
        VoucherList voucherWalletList = voucherWalletService.getVoucherWalletList();
        List<Voucher> voucherWallet = voucherWalletList.getVouchers();
        model.addAttribute("voucherWallet", voucherWallet);
        return "voucherWallet/voucherWalletList";
    }

    @GetMapping("/walletHome")
    public String walletHome() {
        return "voucherWallet/walletHome";
    }

    @GetMapping("/voucherWallet/ownableList")
    public String showOwnableVoucherList(Model model) {
        VoucherList ownableVoucherList = voucherService.getOwnableVoucherList();
        List<Voucher> vouchers = ownableVoucherList.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucherWallet/ownableVoucherList";
    }
}
