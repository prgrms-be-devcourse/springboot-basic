package org.prgrms.kdtspringdemo.wallet.controller;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.prgrms.kdtspringdemo.dto.AddVoucherToWalletDto;
import org.prgrms.kdtspringdemo.dto.WalletViewDto;
import org.prgrms.kdtspringdemo.wallet.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wallets")
public class WalletWebController {
    private final WalletService walletService;
    private final VoucherService voucherService;

    public WalletWebController(WalletService walletService, VoucherService voucherService) {
        this.walletService = walletService;
        this.voucherService = voucherService;
    }

    @GetMapping
    public String listWallets(Model model) {
        List<WalletViewDto> walletViewDtoList = walletService.getWalletViewDtoList();
        model.addAttribute("walletList", walletViewDtoList);
        return "wallet";
    }

    @GetMapping("/{walletId}")
    public String viewWallet(@PathVariable UUID walletId, Model model) {
        WalletViewDto walletViewDto = walletService.findById(walletId).orElseThrow(NoSuchFieldError::new);
        List<VoucherViewDto> voucherViewDtoList = walletService.findVouchersById(walletViewDto.getCustomerId());
        walletViewDto.setVoucherList(voucherViewDtoList);
        List<VoucherViewDto> unallocatedVouchers = voucherService.findUnallocatedVoucher();

        model.addAttribute("wallet", walletViewDto);
        model.addAttribute("voucherList", unallocatedVouchers);
        model.addAttribute("addVoucherToWalletDto", new AddVoucherToWalletDto());
        return "wallet_details";
    }

    @PostMapping("/{walletId}/add-voucher")
    public String addVoucherToWallet(
            @PathVariable UUID walletId,
            @ModelAttribute AddVoucherToWalletDto addVoucherToWalletDto) {

        UUID selectedVoucherId = addVoucherToWalletDto.getSelectedVoucherId();
        if (selectedVoucherId != null) {
            WalletViewDto walletViewDto = walletService.findById(walletId).orElse(null);
            VoucherViewDto selectedVoucher = voucherService.findById(selectedVoucherId);

            if (walletViewDto != null && selectedVoucher != null) {
                walletService.addVoucherByCustomerId(walletViewDto.getWalletId(), walletViewDto.getCustomerId(), selectedVoucherId);
            }
        }

        return "redirect:/wallets/{walletId}";
    }

    @GetMapping("/{walletId}/delete")
    public String deleteById(@PathVariable UUID walletId) {
        walletService.deleteById(walletId);
        return "redirect:/wallets";
    }

    @GetMapping("/{walletId}/delete/{voucherId}")
    public String deleteVoucher(@PathVariable UUID walletId, @PathVariable UUID voucherId) {
        walletService.deleteVoucherByVoucherId(walletId, voucherId);
        return "redirect:/wallets";
    }
}
