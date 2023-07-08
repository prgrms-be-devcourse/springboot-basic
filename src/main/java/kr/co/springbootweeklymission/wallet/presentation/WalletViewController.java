package kr.co.springbootweeklymission.wallet.presentation;

import kr.co.springbootweeklymission.member.presentation.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.wallet.application.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/v1/wallets")
public class WalletViewController {
    private final WalletService walletService;

    @GetMapping("/members/{member_id}")
    public String getVouchersByMemberId(Model model,
                                        @PathVariable(name = "member_id") UUID memberId) {
        List<VoucherResDTO.READ> reads = walletService.getVouchersByMemberId(memberId);
        model.addAttribute("vouchers", reads);
        model.addAttribute("memberId", memberId);
        return "wallet/getVouchersByMemberId";
    }

    @GetMapping("/vouchers/{voucher_id}")
    public String getMemberByVoucherId(Model model,
                                       @PathVariable(name = "voucher_id") UUID voucherId) {
        MemberResDTO.READ read = walletService.getMemberByVoucherId(voucherId);
        model.addAttribute("member", read);
        model.addAttribute("voucherId", voucherId);
        return "wallet/getMemberByVoucherId";
    }
}
