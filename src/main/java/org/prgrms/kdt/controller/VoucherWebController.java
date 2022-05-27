package org.prgrms.kdt.controller;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.prgrms.kdt.controller.dto.CreateVoucherRequest;
import org.prgrms.kdt.controller.dto.VoucherResponse;
import org.prgrms.kdt.controller.dto.VouchersResponse;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vouchers")
public class VoucherWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String vouchersPage(Model model) {
        List<VouchersResponse> vouchers = this.voucherService.getVouchers()
            .stream()
            .map(VouchersResponse::of)
            .toList();
        model.addAttribute("vouchers", vouchers);

        return "vouchers";
    }

    @GetMapping("/{id}")
    public String voucherPage(@PathVariable UUID id, Model model) {
        Voucher findVoucher = this.voucherService.getVoucher(id);
        VoucherResponse voucherResponse = new VoucherResponse(
            findVoucher.getId(),
            findVoucher.getValue(),
            findVoucher.getVoucherType(),
            findVoucher.getCreatedAt()
        );
        model.addAttribute("voucher", voucherResponse);

        return "voucher";
    }

    @GetMapping("/new-voucher")
    public String newVoucherPage() {
        return "new-voucher";
    }

    @PostMapping("/new-voucher")
    public String newVoucher(@Valid CreateVoucherRequest voucherRequest) {
        this.voucherService.makeVoucher(voucherRequest.getType(), voucherRequest.getValue());

        return "redirect:/vouchers";
    }

    @GetMapping("/{id}/update")
    public String updateVoucherPage(@PathVariable UUID id, Model model) {
        model.addAttribute("voucher", this.voucherService.getVoucher(id));

        return "update-voucher";
    }

    @PostMapping("/{id}/update")
    public String updateVoucher(@PathVariable UUID id, long value) {
        this.voucherService.updateVoucher(id, value);

        return "redirect:/vouchers/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteVoucher(@PathVariable UUID id) {
        this.voucherService.deleteVoucher(id);

        return "redirect:/vouchers";
    }

}
