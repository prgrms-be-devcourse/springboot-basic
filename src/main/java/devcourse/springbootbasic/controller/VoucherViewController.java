package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherViewController {

    private final VoucherService voucherService;

    @GetMapping()
    public String findAllVouchers(Model model) {
        List<VoucherFindResponse> allVouchers = this.voucherService.findAllVouchers();
        model.addAttribute("vouchers", allVouchers);

        return "voucher/voucher-list";
    }

    @GetMapping("/create")
    public String create() {
        return "voucher/voucher-form";
    }

    @PostMapping("/create")
    public String createVoucher(@ModelAttribute VoucherCreateRequest voucherCreateRequest) {
        this.voucherService.createVoucher(voucherCreateRequest);
        return "redirect:/voucher";
    }

    @PostMapping("/delete/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        System.out.println("voucherId = " + voucherId);
        voucherService.deleteVoucher(voucherId);

        return "redirect:/voucher";
    }
}
