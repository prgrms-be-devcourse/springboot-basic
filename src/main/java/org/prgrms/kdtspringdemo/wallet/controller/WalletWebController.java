package org.prgrms.kdtspringdemo.wallet.controller;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.prgrms.kdtspringdemo.wallet.domain.dto.AddVoucherToWalletDto;
import org.prgrms.kdtspringdemo.wallet.domain.dto.WalletDetailsDto;
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
        List<Wallet> walletList = walletService.findAll();
        model.addAttribute("walletList", walletList);
        return "wallet";
    }

    @GetMapping("/{walletId}")
    public String viewWallet(@PathVariable UUID walletId, Model model) {
        Wallet wallet = walletService.findById(walletId).orElse(null);
        List<Voucher> vouchers = walletService.findVouchersById(wallet.getCustomerId());
        List<VoucherViewDto> voucherViewDtos = new ArrayList<>();
        vouchers.stream().forEach(voucher -> voucherViewDtos.add(new VoucherViewDto(voucher)));

        WalletDetailsDto walletDetailsDto = new WalletDetailsDto(walletId, wallet.getCustomerId(), voucherViewDtos);

        List<Voucher> unallocatedVouchers = voucherService.findUnallocatedVoucher();

        model.addAttribute("wallet", walletDetailsDto);
        model.addAttribute("voucherList", unallocatedVouchers);
        model.addAttribute("addVoucherToWalletDto", new AddVoucherToWalletDto());
        return "wallet_details";
    }

    @PostMapping("/{walletId}/addVoucher")
    public String addVoucherToWallet(
            @PathVariable UUID walletId,
            @ModelAttribute AddVoucherToWalletDto addVoucherToWalletDto) {

        UUID selectedVoucherId = addVoucherToWalletDto.getSelectedVoucherId();
        if (selectedVoucherId != null) {
            Wallet wallet = walletService.findById(walletId).orElse(null);
            Voucher selectedVoucher = voucherService.findById(selectedVoucherId);

            if (wallet != null && selectedVoucher != null) {
                walletService.addVoucherByCustomerId(wallet.getWalletId(), wallet.getCustomerId(), selectedVoucherId);
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
