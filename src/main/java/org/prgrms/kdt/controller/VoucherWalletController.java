package org.prgrms.kdt.controller;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherList;
import org.prgrms.kdt.service.VoucherWalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VoucherWalletController {

    private final VoucherWalletService voucherWalletService;

    public VoucherWalletController(VoucherWalletService voucherWalletService) {
        this.voucherWalletService = voucherWalletService;
    }

    @GetMapping("/voucherWallet")
    public String showVoucherList(Model model) {
        VoucherList voucherWalletList = voucherWalletService.getVoucherWalletList();
        List<Voucher> voucherWallet = voucherWalletList.getVouchers();
        model.addAttribute("voucherWallet", voucherWallet);
        System.out.println("found");
        return "voucherWallet/voucherWalletList";
    }
}
