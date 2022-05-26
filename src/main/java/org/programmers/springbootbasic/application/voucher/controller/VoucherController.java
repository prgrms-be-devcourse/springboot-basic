package org.programmers.springbootbasic.application.voucher.controller;

import org.programmers.springbootbasic.application.voucher.controller.api.CreateVoucherRequest;
import org.programmers.springbootbasic.application.voucher.controller.api.UpdateVoucherRequest;
import org.programmers.springbootbasic.application.voucher.service.DefaultVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class VoucherController {

    private final DefaultVoucherService defaultVoucherService;

    private final VoucherConverter voucherConverter;

    public VoucherController(DefaultVoucherService defaultVoucherService, VoucherConverter voucherConverter) {
        this.defaultVoucherService = defaultVoucherService;
        this.voucherConverter = voucherConverter;
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        var voucherResponseList = defaultVoucherService
                .getVoucherList().stream()
                .map(voucherConverter::convertVoucherDto)
                .toList();
        model.addAttribute("vouchers", voucherResponseList);
        return "vouchers";
    }

    @GetMapping("/{voucherId}")
    public String getVoucher(Model model, @PathVariable UUID voucherId) {
        var getVoucher = defaultVoucherService.getVoucher(voucherId);
        var voucher = voucherConverter.convertVoucherDto(getVoucher);

        model.addAttribute("voucher", voucher);
        return "voucher-details";
    }

    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage() {
        return "new-vouchers";
    }

    @PutMapping("/vouchers/details/update")
    public String updateVoucher(UpdateVoucherRequest updateVoucherRequest) {
        defaultVoucherService.updateVoucher(updateVoucherRequest);
        return "redirect:/vouchers";
    }

    @PostMapping("/vouchers/new")
    public String addNewVoucher(CreateVoucherRequest createVoucherRequest) {
        defaultVoucherService.createVoucher(createVoucherRequest);
        return "redirect:/vouchers";
    }

    @DeleteMapping("/vouchers/details/delete")
    public String deleteVoucher(@RequestParam UUID voucherId) {
        defaultVoucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
