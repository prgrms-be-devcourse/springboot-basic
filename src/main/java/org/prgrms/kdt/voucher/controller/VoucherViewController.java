//package org.prgrms.kdt.voucher.controller;
//
//import org.prgrms.kdt.voucher.controller.dto.CreateVoucherRequest;
//import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
//import org.prgrms.kdt.voucher.service.VoucherService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/voucher")
//public class VoucherViewController {
//    private final VoucherService voucherService;
//
//    public VoucherViewController(VoucherService voucherService) {
//        this.voucherService = voucherService;
//    }
//
//    @PostMapping
//    public String create(@RequestBody CreateVoucherRequest request) {
//        voucherService.createVoucher(request);
//        return "redirect:/voucher";
//    }
//
//    @GetMapping
//    public String findAll(Model model) {
//        VoucherResponses response = voucherService.findAll();
//        model.addAttribute("vouchers", response);
//        return "voucher/vouchers";
//    }
//}
