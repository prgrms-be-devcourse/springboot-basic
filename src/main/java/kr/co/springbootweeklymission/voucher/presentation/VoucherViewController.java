package kr.co.springbootweeklymission.voucher.presentation;

import kr.co.springbootweeklymission.voucher.application.VoucherService;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/v1/vouchers")
public class VoucherViewController {
    private final VoucherService voucherService;

    @GetMapping
    public String getVouchersAll(Model model) {
        List<VoucherResDTO.READ> reads = voucherService.getVouchersAll();
        model.addAttribute("vouchers", reads);
        return "voucher/getVouchersAll";
    }
}
