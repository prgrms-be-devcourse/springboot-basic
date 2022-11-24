package com.example.springbootbasic.controller.voucher;

import com.example.springbootbasic.controller.request.CreateVoucherRequest;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.dto.voucher.VoucherDto;
import com.example.springbootbasic.service.voucher.JdbcVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewVoucherController {

    private final JdbcVoucherService voucherService;

    public ViewVoucherController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/v1/vouchers")
    public String voucherList(Model model) {
        List<Voucher> findAllVouchers = voucherService.findAllVouchers();
        List<VoucherDto> result = findAllVouchers.stream()
                .map(VoucherDto::newInstance)
                .toList();
        model.addAttribute("vouchers", result);
        return "voucher-list";
    }

    @GetMapping("/v1/voucher-add")
    public String voucherAddForm() {
        return "voucher-add";
    }

    @PostMapping("/v1/voucher-add")
    public String voucherAddForm(CreateVoucherRequest request) {
        long discountValue = request.discountValue();
        VoucherType voucherType = VoucherType.of(request.voucherType());
        voucherService.saveVoucher(VoucherFactory.of(discountValue, voucherType));
        return "redirect:vouchers";
    }

    @DeleteMapping("/v1/vouchers/{voucherId}")
    public String deleteVoucher(@PathVariable Long voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return "redirect:";
    }
}
