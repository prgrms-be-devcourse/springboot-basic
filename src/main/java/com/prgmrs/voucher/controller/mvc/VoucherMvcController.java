package com.prgmrs.voucher.controller.mvc;

import com.prgmrs.voucher.controller.mvc.dto.VoucherMvcCreateRequest;
import com.prgmrs.voucher.controller.mvc.dto.VoucherMvcRemoveRequest;
import com.prgmrs.voucher.dto.request.VoucherIdRequest;
import com.prgmrs.voucher.dto.request.VoucherRequest;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vouchers")
public class VoucherMvcController {
    private final VoucherService voucherService;

    public VoucherMvcController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/")
    public String showMainPage() {
        return "voucher-main";
    }

    @GetMapping("/list")
    public String showListPage(Model model) {
        VoucherListResponse voucherListResponse = voucherService.findAll();
        List<Voucher> voucherList = voucherListResponse.voucherList();
        model.addAttribute("voucherList", voucherList);
        return "voucher-list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("voucherCreateRequest", new VoucherMvcCreateRequest());
        return "voucher-create";
    }

    @PostMapping("/create")
    public String createVoucher(@ModelAttribute VoucherMvcCreateRequest voucherMvcCreateRequest) {
        voucherService.createVoucher(new VoucherRequest(voucherMvcCreateRequest.getDiscountType(), voucherMvcCreateRequest.getDiscountStringValue()));
        return "voucher-request-success";
    }

    @GetMapping("/remove")
    public String showRemovePage(Model model) {
        VoucherListResponse voucherListResponse = voucherService.findAll();
        List<Voucher> voucherList = voucherListResponse.voucherList();
        model.addAttribute("voucherList", voucherList);
        model.addAttribute("voucherRemoveRequest", new VoucherMvcRemoveRequest());
        return "voucher-remove";
    }

    @PostMapping("/remove")
    public String removeVoucher(@ModelAttribute VoucherMvcRemoveRequest voucherMvcRemoveRequest) {
        voucherService.removeVoucher(new VoucherIdRequest(voucherMvcRemoveRequest.getVoucherId()));
        return "voucher-request-success";
    }
}
