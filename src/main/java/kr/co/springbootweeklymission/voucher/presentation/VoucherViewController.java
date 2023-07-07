package kr.co.springbootweeklymission.voucher.presentation;

import kr.co.springbootweeklymission.voucher.application.VoucherService;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/v1/vouchers")
public class VoucherViewController {
    private final VoucherService voucherService;

    @GetMapping("/post-page")
    public String createPage() {
        return "voucher/createVoucher";
    }

    @GetMapping("/put-page/{voucher_id}")
    public String updatePage(Model model,
                             @PathVariable(name = "voucher_id") UUID voucherId) {
        model.addAttribute("voucherId", voucherId);
        return "voucher/updateVoucherById";
    }

    @PostMapping
    public String createVoucher(@ModelAttribute @Validated VoucherReqDTO.CREATE create) {
        voucherService.createVoucher(create);
        return "redirect:/view/v1/vouchers";
    }


    @PostMapping("/put/{voucher_id}")
    public String updateVoucherById(@PathVariable(name = "voucher_id") UUID voucherId,
                                    @ModelAttribute @Validated VoucherReqDTO.UPDATE update) {
        voucherService.updateVoucherById(voucherId, update);
        return "redirect:/view/v1/vouchers/" + voucherId;
    }

    @PostMapping("/delete/{voucher_id}")
    public String deleteVoucherById(@PathVariable(name = "voucher_id") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return "redirect:/view/v1/vouchers";
    }

    @GetMapping
    public String getVouchersAll(Model model) {
        List<VoucherResDTO.READ> reads = voucherService.getVouchersAll();
        model.addAttribute("vouchers", reads);
        return "voucher/getVouchersAll";
    }

    @GetMapping("/{voucher_id}")
    public String getVoucherById(Model model,
                                 @PathVariable(name = "voucher_id") UUID voucherId) {
        VoucherResDTO.READ read = voucherService.getVoucherById(voucherId);
        model.addAttribute("voucher", read);
        return "voucher/getVoucherById";
    }
}
