package org.prgrms.kdt.controller.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String voucherPage(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        List<VoucherDto> vouchersDto = vouchers.stream()
            .map(VoucherDto::new).toList();

        model.addAttribute("vouchers", vouchersDto);

        return "voucher-list";
    }

    @GetMapping("/voucher/new")
    public String newVoucherPage() {
        return "voucher-new";
    }

    @PostMapping("/vouchers")
    public String createVoucher(CreateVoucherRequest request) {
        voucherService.create(UUID.randomUUID(), request.getVoucherValue(), request.getVoucherType());

        return "redirect:/vouchers";
    }

    @PostMapping("/voucher/delete")
    public String deleteVoucher(@RequestParam UUID voucherId) {
        voucherService.delete(voucherId);

        return "redirect:/vouchers";
    }

    @GetMapping("/voucher/details/{voucherId}")
    public String voucherDetailsPage(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher",
            new VoucherDto(voucher)
        );

        return "voucher-details";
    }

    @PostMapping("/voucher/update")
    public String updateVoucher(UpdateVoucherRequest request) {
        voucherService.update(request.getVoucherId(), request.getVoucherValue());

        return "redirect:/vouchers";
    }
}
