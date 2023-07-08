package kr.co.springbootweeklymission.wallet.presentation;

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

    @GetMapping("/{member_id}")
    public String getVouchersByMemberId(Model model,
                                        @PathVariable(name = "member_id") UUID memberId) {
        List<VoucherResDTO.READ> reads = walletService.getVouchersByMemberId(memberId);
        model.addAttribute("vouchers", reads);
        return "wallet/getVouchersByMemberId";
    }
}
