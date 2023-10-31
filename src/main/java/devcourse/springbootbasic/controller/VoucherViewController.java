package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherViewController {

    private final VoucherService voucherService;

    @GetMapping()
    public String findAllVouchers(Model model) {
        System.out.println("findAllVouchers");
        List<VoucherFindResponse> allVouchers = this.voucherService.findAllVouchers();
        model.addAttribute("vouchers", allVouchers);

        return "voucher/voucher-list";
    }
}
