package com.prgms.vouchermanager.contorller.wallet;

import com.prgms.vouchermanager.domain.wallet.Wallet;
import com.prgms.vouchermanager.dto.CreateWalletDto;
import com.prgms.vouchermanager.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;


@Controller
@Slf4j
@RequestMapping("/admin/wallets")
@RequiredArgsConstructor
public class WebWalletController {

    private final WalletService walletService;

    @GetMapping("/customer/{customerId}")
    String findByCustomerId(@PathVariable Long customerId, Model model) {
        List<Wallet> wallets = walletService.findByCustomerId(customerId);
        model.addAttribute("wallets", wallets);
        return "wallet_list.html";
    }

    @GetMapping("/voucher/{voucherId}")
    String findByVoucherId(@PathVariable UUID voucherId, Model model) {
        Wallet wallet = walletService.findByVoucherId(voucherId);
        model.addAttribute("wallets", wallet);
        return "wallet_list.html";
    }

    @GetMapping("")
    String findAll(Model model) {
        List<Wallet> wallets = walletService.findAll();
        model.addAttribute("wallets", wallets);

        return "wallet_list.html";
    }

    @GetMapping("/create")
    String getCreateForm() {
        return "wallet_create.html";
    }

    @PostMapping("/create")
    String create(@ModelAttribute CreateWalletDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("code", "입력값 타입 형태가 잘못되었습니다.");
            return "wallet_exception.html";
        }
        Wallet wallet = walletService.save(dto);
        redirectAttributes.addAttribute("id", wallet.getId());
        return "redirect:/admin/wallets";
    }

    @DeleteMapping("/{id}")
    String deleteByCustomerId(@PathVariable Long id) {
        walletService.deleteByCustomerId(id);
        return "redirect:/admin/wallets";
    }

    @ExceptionHandler
    String NotExistInfo(DataIntegrityViolationException e1, EmptyResultDataAccessException e2, Model model) {
        model.addAttribute("code", "회원번호 또는 바우처번호가 존재하지 않습니다.");
        return "wallet_exception.html";
    }

}
