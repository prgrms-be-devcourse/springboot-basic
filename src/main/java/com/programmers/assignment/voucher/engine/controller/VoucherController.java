package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.model.Voucher;
import com.programmers.assignment.voucher.engine.service.CustomerService;
import com.programmers.assignment.voucher.engine.service.VoucherService;
import com.programmers.assignment.voucher.util.domain.VoucherVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;
    private final CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @GetMapping("/vouchers")
    public String getVoucherList(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher";
    }

    @GetMapping("/vouchers/new")
    public String createVoucher(Model model) {
        var customers =customerService.findCustomers();
        model.addAttribute("customers", customers);
        return "voucher-form";
    }

    @PostMapping("/vouchers/new")
    public String createVoucher(HttpServletRequest request) {
        var customerId = request.getParameter("customerId");
        System.out.println(customerId + "controller ID");
        var discountWay = request.getParameter("discountWay");
        var discountValue = request.getParameter("discountValue");
        voucherService.makeVoucher(customerId, discountWay, discountValue);
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String voucherDetails(Model model, @PathVariable UUID voucherId) {
        Voucher voucher = voucherService.getVoucherById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher-details";
    }

    public void makeVoucher(String inputDiscountWay) {
        String discountWay = VoucherVariable.chooseDiscountWay(inputDiscountWay);
//        voucherService.makeVoucher(discountWay);
    }
}
