package org.prgrms.voucherapplication.domain.voucher.controller;

import org.prgrms.voucherapplication.domain.voucher.controller.dto.CreateVoucherRequest;
import org.prgrms.voucherapplication.domain.voucher.entity.Voucher;
import org.prgrms.voucherapplication.domain.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherViewController {

    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String viewVoucherPage(Model model) {
        List<Voucher> allVouchers = voucherService.findAll();
        model.addAttribute("vouchers", allVouchers);
        return "vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewNewCustomerPage() {
        return "new-vouchers";
    }

    @PostMapping("/vouchers/new")
    public String viewCreateVoucher(CreateVoucherRequest createVoucherRequest) {
        VoucherType voucherType = createVoucherRequest.getVoucherType();
        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), createVoucherRequest.getDiscount(), LocalDateTime.now());
        voucherService.create(voucher);
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String findCustomer(@PathVariable("voucherId") UUID voucherId, Model model) {
        Optional<Voucher> maybeVoucher = voucherService.findById(voucherId);
        if (maybeVoucher.isPresent()) {
            model.addAttribute("voucher", maybeVoucher.get());
            return "voucher-details";
        } else {
            return "404";
        }
    }

    @PostMapping("/vouchers/{voucherId}/delete")
    public String deleteCustomer(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "redirect:/vouchers";
    }
}
