package kr.co.programmers.springbootbasic.voucher.controller;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherDeleteResponse;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Profile("web")
@Controller
@RequestMapping("/vouchers")
public class WebVoucherController {
    private final VoucherService voucherService;

    public WebVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/home")
    public String loadHomePage() {
        return "voucherTemplate/home";
    }

    @GetMapping("/voucher")
    public String loadVoucherCreatePage() {
        return "voucherTemplate/create";
    }

    @PostMapping("/voucher")
    public String createVoucher(@RequestParam("voucherType") VoucherType type,
                                @RequestParam("amount") long amount,
                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("redirectUrl", "/vouchers/voucher");
        try {
            VoucherResponse voucherResponse = voucherService.createVoucher(type, amount);
            String message = voucherResponse.formatMessage();
            redirectAttributes.addFlashAttribute("message", message);

            return "redirect:/vouchers/success";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "redirect:/vouchers/fail";
        }
    }

    @GetMapping("/list")
    public String loadVoucherListPage(Model model) {
        List<VoucherResponse> voucherResponses = voucherService.listAllVoucher();
        model.addAttribute("voucherResponses", voucherResponses);

        return "voucherTemplate/list";
    }

    @PostMapping("/list/voucher")
    public String deleteById(@RequestParam("voucherId") UUID voucherId,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("redirectUrl", "/vouchers/list/voucher");

        try {
            voucherService.deleteById(voucherId);
            VoucherDeleteResponse response = new VoucherDeleteResponse(voucherId, true);
            String message = response.formatVoucherDeleteResponse();
            redirectAttributes.addFlashAttribute("message", message);

            return "redirect:/vouchers/success";
        } catch (RuntimeException e) {
            VoucherDeleteResponse response = new VoucherDeleteResponse(voucherId, false);
            String error = response.formatVoucherDeleteResponse();
            redirectAttributes.addFlashAttribute("error", error);

            return "redirect:/vouchers/fail";
        }
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
