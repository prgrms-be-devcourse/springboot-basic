package kr.co.programmers.springbootbasic.wallet.controller;

import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import kr.co.programmers.springbootbasic.wallet.service.WalletService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Profile("web")
@RequestMapping("/wallets")
public class WebWalletController {
    private final WalletService walletService;

    public WebWalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/home")
    public String loadHomePage() {
        return "walletTemplate/home";
    }

    @GetMapping("/save")
    public String loadSaveVoucherInWalletPage() {
        return "walletTemplate/save";
    }

    @PostMapping("/save")
    public String saveVoucherInWallet(@ModelAttribute WalletSaveDto saveDto,
                                      RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("redirectUrl", "/wallets/save");

        WalletSaveDto responseDto = walletService.saveVoucherInCustomerWallet(saveDto);
        String message = responseDto.formatMessage();
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/wallets/success";
    }

    @PostMapping("/list")
    public String loadListAllVoucherInWalletPage(@RequestParam("walletId") String walletId, Model model) {
        List<VoucherResponse> responses = walletService.findWalletById(walletId);
        model.addAttribute("walletResponse", responses);

        return "walletTemplate/list";
    }

    @GetMapping("/success")
    public String loadServiceSuccessPage() {
        return "success";
    }

    @GetMapping("/fail")
    public String loadServiceFailPage() {
        return "fail";
    }
}
