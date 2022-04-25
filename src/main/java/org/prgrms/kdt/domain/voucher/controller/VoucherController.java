package org.prgrms.kdt.domain.voucher.controller;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.service.CustomerService;
import org.prgrms.kdt.domain.voucher.dto.VoucherAssignRequest;
import org.prgrms.kdt.domain.voucher.dto.VoucherCreateRequest;
import org.prgrms.kdt.domain.voucher.dto.VoucherUpdateRequest;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("vouchers")
public class VoucherController {

    private final VoucherService voucherService;
    private final CustomerService customerService;

    public VoucherController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @GetMapping
    public String voucherList(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers/list";
    }

    @GetMapping("/search")
    public String VoucherListOwnCustomer(Model model, @RequestParam String email) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);
        customer.ifPresent(value -> {
            List<Voucher> vouchers = voucherService
                    .getVouchersByCustomerId(value.getCustomerId());
            model.addAttribute("vouchers", vouchers);
        });
        return "vouchers/list";
    }

    @GetMapping("/{voucherId}")
    public String voucherDetails(Model model, @PathVariable("voucherId") UUID voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucherById(voucherId);
        voucher.ifPresent(value -> model.addAttribute("voucher", value));
        model.addAttribute("voucherType", VoucherType.values());
        return "vouchers/detail";
    }

    @GetMapping("/new")
    public String voucherCreateShow(Model model) {
        model.addAttribute("voucherType", VoucherType.values());
        return "vouchers/create";
    }

    @PostMapping("/new")
    public String voucherCreate(@Valid VoucherCreateRequest createRequest) {
        voucherService.save(createRequest);
        return "redirect:/vouchers";
    }

    @PutMapping("/{voucherId}")
    public String voucherModify(@Valid VoucherUpdateRequest updateRequest,
                                @PathVariable("voucherId") UUID voucherId) {
        voucherService.update(updateRequest, voucherId);
        return "redirect:/vouchers";
    }

    @DeleteMapping("/{voucherId}")
    public String voucherRemove(@PathVariable("voucherId") UUID voucherId) {
        voucherService.remove(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/assign")
    public String voucherAssignShow(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers/assign";
    }

    @PostMapping("/assign")
    public String voucherAssign(VoucherAssignRequest assignRequest) {
        Optional<Customer> customer = customerService.getCustomerByEmail(assignRequest.getEmail());
        customer.ifPresent(value ->
                voucherService.updateCustomerId(assignRequest.getVoucherId(), value.getCustomerId()));
        return "redirect:/";
    }
}
