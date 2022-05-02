package org.prgrms.springbootbasic.engine.controller;

import org.prgrms.springbootbasic.engine.controller.dto.CustomerResponseDto;
import org.prgrms.springbootbasic.engine.controller.dto.VoucherCreateRequestDto;
import org.prgrms.springbootbasic.engine.controller.dto.VoucherResponseDto;
import org.prgrms.springbootbasic.engine.controller.dto.VoucherUpdateRequestDto;
import org.prgrms.springbootbasic.engine.domain.Customer;
import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.service.CustomerService;
import org.prgrms.springbootbasic.engine.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.prgrms.springbootbasic.engine.util.UUIDUtil.convertStringToUUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    private final CustomerService customerService;

    public VoucherController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        List<VoucherResponseDto> allVouchers = voucherService
                .getAllVouchers()
                .stream()
                .map(VoucherResponseDto::new)
                .collect(Collectors.toList());
        model.addAttribute("vouchers", allVouchers);
        return "views/vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewVoucherCreatePage(Model model) {
        List<CustomerResponseDto> customers = customerService.getAllCustomers().stream().map(CustomerResponseDto::new).toList();
        model.addAttribute("customers", customers);
        return "views/new-voucher";
    }

    @PostMapping("/vouchers/new")
    public String createNewVoucher(VoucherCreateRequestDto voucherCreateRequestDto) {
        Voucher voucher = voucherCreateRequestDto.toEntity();
        if (voucherCreateRequestDto.getCustomerId().isPresent()) {
            Customer customer = customerService.getCustomerById(voucherCreateRequestDto.getCustomerId().get());
            voucher.changeOwner(customer);
        }
        voucherService.insertVoucher(voucher);
        return "redirect:/vouchers/" + voucher.getVoucherId();
    }

    @GetMapping("/vouchers/{voucherId}")
    public String viewVoucherDetailPage(Model model, @PathVariable String voucherId) {
        UUID id = convertStringToUUID(voucherId);
        Voucher voucher = voucherService.getVoucher(id);
        model.addAttribute("voucher", new VoucherResponseDto(voucher));
        return "views/voucher";
    }

    @GetMapping("/vouchers/{voucherId}/edit")
    public String viewVoucherEditPage(Model model, @PathVariable String voucherId) {
        UUID id = convertStringToUUID(voucherId);
        Voucher voucher = voucherService.getVoucher(id);
        model.addAttribute("voucher", new VoucherResponseDto(voucher));
        model.addAttribute("customers", customerService.getAllCustomers());
        return "views/update-voucher";
    }

    @PostMapping("/vouchers/update")
    public String updateVoucher(VoucherUpdateRequestDto voucherUpdateRequestDto) {
        Voucher voucher = voucherService.getVoucher(voucherUpdateRequestDto.getVoucherId());
        if (voucherUpdateRequestDto.getCustomerId().isPresent()) {
            Customer customer = customerService.getCustomerById(voucherUpdateRequestDto.getCustomerId().get());
            voucher.changeOwner(customer);
        } else {
            voucher.revokeOwner();
        }
        voucher.changeValue(voucherUpdateRequestDto.getValue());
        voucherService.updateVoucher(voucher);
        return "redirect:/vouchers/" + voucher.getVoucherId();
    }

    @PostMapping("/vouchers/{voucherId}/delete")
    public String deleteVoucher(@PathVariable String voucherId) {
        UUID id = convertStringToUUID(voucherId);
        voucherService.removeVoucherById(id);
        return "redirect:/vouchers";
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("exception", ex);
        return "views/error/400";
    }

    @ExceptionHandler({Exception.class})
    public String handleException(Exception ex, Model model) {
        model.addAttribute("exception", ex);
        return "views/error/500";
    }
}
