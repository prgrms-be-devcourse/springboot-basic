package org.prgrms.kdt.domain.voucher.controller;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.service.CustomerService;
import org.prgrms.kdt.domain.voucher.request.VoucherAssignRequest;
import org.prgrms.kdt.domain.voucher.request.VoucherCreateRequest;
import org.prgrms.kdt.domain.voucher.request.VoucherSearchRequest;
import org.prgrms.kdt.domain.voucher.request.VoucherUpdateRequest;
import org.prgrms.kdt.domain.voucher.exception.VoucherDataException;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.domain.common.exception.ExceptionType.NOT_SAVED;

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
        List<Voucher> vouchers = voucherService.getAllVouchers(new VoucherSearchRequest());
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
    public String voucherCreatePage(Model model) {
        model.addAttribute("voucherType", VoucherType.values());
        return "vouchers/create";
    }

    @PostMapping("/new")
    public String voucherCreate(@Valid VoucherCreateRequest createRequest) {
        Voucher voucher = createRequest.toEntity();
        voucherService.save(voucher);
        return "redirect:/vouchers";
    }

    @PutMapping("/{voucherId}")
    public String voucherModify(@Valid VoucherUpdateRequest updateRequest,
                                @PathVariable("voucherId") UUID voucherId) {
        voucherService.update(voucherId,
                updateRequest.getVoucherType(),
                updateRequest.getDiscountValue());
        return "redirect:/vouchers";
    }

    @DeleteMapping("/{voucherId}")
    public String voucherRemove(@PathVariable("voucherId") UUID voucherId) {
        voucherService.remove(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/assign")
    public String voucherAssignPage(Model model) {
        List<Voucher> vouchers = voucherService.getVouchersNotAssignedToCustomer();
        model.addAttribute("vouchers", vouchers);
        model.addAttribute("assignForm", new VoucherAssignRequest());
        return "vouchers/assign";
    }

    @PostMapping("/assign")
    public String voucherAssign(
            @ModelAttribute("assignForm") @Valid VoucherAssignRequest assignRequest,
            BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            List<Voucher> vouchers = voucherService.getVouchersNotAssignedToCustomer();
            model.addAttribute("vouchers", vouchers);
            return "/vouchers/assign";
        }
        Customer customer = customerService.getCustomerByEmail(assignRequest.getEmail())
                .orElseThrow(() -> new VoucherDataException(NOT_SAVED));
        voucherService.updateCustomerId(assignRequest.getVoucherId(), customer.getCustomerId());
        return "redirect:/";
    }
}
