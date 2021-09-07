package com.programmers.kdtspringorder.voucher.controller;

import com.programmers.kdtspringorder.voucher.VoucherType;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    /**
     * 바우처 리스트 조회
     *
     * @param model
     * @return 바우처 리스트
     */
    @GetMapping("/vouchers")
    public String findVouchers(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "views/vouchers/voucher-list";
    }


    /**
     * 특정 고객에게 할당된 모든 바우처 조회
     *
     * @param customerId
     * @param model
     * @return
     */
    @GetMapping("/vouchers/{customerId}")
    public String findVouchersByCustomerId(@PathVariable UUID customerId, Model model) {
        List<Voucher> vouchers = voucherService.findByCustomerId(customerId);
        model.addAttribute("vouchers", vouchers);
        return "views/vouchers/voucher-list";
    }

    @GetMapping("/vouchers/deallocate/{voucherId}")
    public String deallocateVoucher(@PathVariable UUID voucherId) {
        voucherService.deallocateVoucher(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/nocustomer/{customerId}")
    public String findNoAllocatedVouchers(@PathVariable UUID customerId, Model model) {
        List<Voucher> vouchers = voucherService.findAllWithoutCustomerId();
        model.addAttribute("vouchers", vouchers);
        model.addAttribute("customerId", customerId);
        return "views/vouchers/voucher-allocate-list";
    }

    @GetMapping("/vouchers/allocate/{voucherId}/{customerId}")
    public String allocateVoucherToCustomer(@PathVariable UUID voucherId, @PathVariable UUID customerId) {
        voucherService.allocateVoucher(voucherId, customerId);
        return "redirect:/vouchers/" + customerId;
    }

    /**
     * 바우처 상세 페이지 조회
     *
     * @param voucherId
     * @param model
     * @return 바우처 상세 정보
     */
    @GetMapping("/vouchers/{voucherId}")
    public String voucherDetails(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findByID(voucherId);
        model.addAttribute("voucher", voucher);
        return "views/vouchers/voucher-details";
    }

    /**
     * 바우처 생성 페이지 조회
     *
     * @return 바우처 생성 페이지
     */
    @GetMapping("/vouchers/new")
    public String createVoucherPage(Model model) {
        model.addAttribute("voucherCreateRequest", new VoucherCreateRequest());
        return "views/vouchers/voucher-creation";
    }

    /**
     * 바우처 생성 요청
     *
     * @param voucherCreateRequest
     * @return 성공시 바우처 조회 페이지로 이동
     */
    @PostMapping("/vouchers/new")
    public String createVoucher(@ModelAttribute VoucherCreateRequest voucherCreateRequest) {
        voucherService.createVoucher(voucherCreateRequest.getVoucherType(), voucherCreateRequest.getValue());
        return "redirect:/vouchers";
    }

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }

    /**
     * 바우처 삭제 요청
     *
     * @param voucherId
     * @return 바우처 조회 페이지로 이동
     */
    @DeleteMapping("/vouchers/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }

}
