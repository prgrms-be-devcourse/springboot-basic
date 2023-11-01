package com.programmers.springbootbasic.domain.userVoucherWallet.presentation;

import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import com.programmers.springbootbasic.domain.userVoucherWallet.application.UserVoucherWalletService;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.CreateUserVoucherWalletRequest;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.UserOwnedVoucherResponse;
import com.programmers.springbootbasic.domain.voucher.application.VoucherService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userVoucher")
public class UserVoucherWalletThymeController {

    private final UserVoucherWalletService userVoucherWalletService;
    private final VoucherService voucherService;

    public UserVoucherWalletThymeController(
        UserVoucherWalletService userVoucherWalletService,
        VoucherService voucherService
    ) {
        this.userVoucherWalletService = userVoucherWalletService;
        this.voucherService = voucherService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        var vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "userVoucher/register";
    }

    @PostMapping("/register")
    public String registerUserVoucher(
        @ModelAttribute CreateUserVoucherWalletRequest request, Model model
    ) {
        Long id = userVoucherWalletService.create(request);
        model.addAttribute("id", id);
        return "userVoucher/registerationComplete";
    }

    @PostMapping("/delete/{id}")
    public String deleteUserVoucher(@PathVariable Long id) {
        userVoucherWalletService.deleteById(id);
        return "userVoucher/deleteComplete";
    }

    @GetMapping("/userByVoucher")
    public String getUserByVoucherIdPage(Model model) {
        var vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "userVoucher/enterVoucherId";
    }

    @GetMapping("/userByVoucher/{voucherId}")
    public String getUserByVoucherId(@PathVariable UUID voucherId, Model model) {
        List<UserResponse> users = userVoucherWalletService.findUserByVoucherId(voucherId);
        model.addAttribute("users", users);
        return "userVoucher/userByVoucherIdResult";
    }

    @GetMapping("/voucherByUser")
    public String getVoucherByUserNicknamePage() {
        return "userVoucher/enterUserNickname";
    }

    @GetMapping("/voucherByUser/{userNickname}")
    public String getVoucherByUserNickname(
        @PathVariable String userNickname, Model model
    ) {
        List<UserOwnedVoucherResponse> vouchers = userVoucherWalletService.findVoucherByUserNickname(
            userNickname);
        model.addAttribute("vouchers", vouchers);
        return "userVoucher/voucherByUserNicknameResult";
    }

}
