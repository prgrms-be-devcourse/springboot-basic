package org.prgrms.kdt.voucher.controller;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // vouchers 관리 페이지 렌더링
    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        // 초기 화면 렌더링시 voucher 리스트 넘겨줌
        var voucherList = voucherService.getVoucherList();
        System.out.println(voucherList.toString());
        model.addAttribute("voucherList", voucherList);
        return "views/vouchers";
    }

    //
    @PostMapping("/vouchers/create")
    public String createVoucher(VoucherDto voucherDto) {
        voucherService.createVoucher(UUID.randomUUID(), voucherDto.type(), voucherDto.value());
        return "redirect:/vouchers";
    }



}
