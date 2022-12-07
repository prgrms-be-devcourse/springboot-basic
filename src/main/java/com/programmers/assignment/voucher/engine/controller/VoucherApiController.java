package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.model.Customer;
import com.programmers.assignment.voucher.engine.model.Voucher;
import com.programmers.assignment.voucher.engine.service.CustomerService;
import com.programmers.assignment.voucher.engine.service.VoucherService;
import com.programmers.assignment.voucher.util.dto.CustomerDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public List<Voucher> getVoucherList() {
        return voucherService.getAllVouchers();
    }

    @PostMapping("/api/v1/vouchers/new")
    public String createVoucher(HttpServletRequest request) {
        var customerId = request.getParameter("customerId");
        var discountWay = request.getParameter("discountWay");
        var discountValue = request.getParameter("discountValue");
        voucherService.makeVoucher(customerId, discountWay, discountValue);
        return "redirect:/vouchers";
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    public Voucher voucherDetails(Model model, @PathVariable UUID voucherId) {
        return voucherService.getVoucherById(voucherId);
    }
}
