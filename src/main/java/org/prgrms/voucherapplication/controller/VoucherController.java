package org.prgrms.voucherapplication.controller;

import org.prgrms.voucherapplication.dto.CreateVoucherRequest;
import org.prgrms.voucherapplication.dto.IssueVoucherRequest;
import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.service.JdbcCustomerService;
import org.prgrms.voucherapplication.service.JdbcVoucherService;
import org.prgrms.voucherapplication.view.io.VoucherType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherController {
    private final JdbcVoucherService voucherService;
    private final JdbcCustomerService customerService;

    public VoucherController(JdbcVoucherService voucherService, JdbcCustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    // 바우처 관리 페이지
    @GetMapping("/vouchers")
    public String voucherManagementPage(Model model) {
        List<SqlVoucher> allVoucher = voucherService.getAllVoucher();
        model.addAttribute("vouchers", allVoucher);
        return "voucher/vouchers";
    }

    // 바우처 상세 페이지
    @GetMapping("/vouchers/{voucherId}")
    public String findVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        Optional<Customer> customer = voucherService.getCustomerByVoucherId(voucherId);
        Optional<SqlVoucher> voucher = voucherService.getVoucherById(voucherId);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
        } else {
            model.addAttribute("customer", null);
        }
        model.addAttribute("voucher", voucher.get());
        return "voucher/voucher-details";

    }

    // 바우처 삭제
    @PostMapping("/vouchers/{voucherId}/delete")
    public String deleteVoucherById(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return "redirect:/vouchers";
    }

    // 바우처 생성하기 페이지
    @GetMapping("/vouchers/new")
    public String newVoucherPage(Model model) {
        model.addAttribute("voucherTypes", VoucherType.values());
        return "voucher/new-voucher";
    }

    // 바우처 추가
    @PostMapping("/vouchers/new")
    public String addNewVoucher(CreateVoucherRequest request) {
        voucherService.saveVoucher(request.voucherType(), request.discountAmount());
        return "redirect:/vouchers";
    }

    // 바우처 발급하기 페이지
    @GetMapping("/vouchers/issue")
    public String VoucherIssuePage(Model model) {
        List<SqlVoucher> unissuedVouchers = voucherService.getUnissuedVouchers();
        List<Customer> allCustomer = customerService.getAllCustomer();
        model.addAttribute("unissuedVouchers", unissuedVouchers);
        model.addAttribute("customers", allCustomer);
        return "voucher/voucher-issue";
    }

    // 바우처 발급
    @PostMapping("/vouchers/issue")
    public String issueVoucher(IssueVoucherRequest request) {
        voucherService.issueVoucherToCustomer(request.voucherId(), request.customerId());
        return "redirect:/vouchers";
    }
}
