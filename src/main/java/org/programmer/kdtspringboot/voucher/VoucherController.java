package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    //초기 메인 화면
    @GetMapping(value = "/")
    public String home(){
        return "views/home";
    }

    //Voucher 목록
    @GetMapping(value = "/vouchers")
    public String viewVouchersPage(Model model) {
        var allCustomers = voucherService.findAllVouchers();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("vouchers",allCustomers);
        return "views/vouchers";
    }

    //새로 생성
    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage(){
        return "views/new-vouchers";
    }

    @GetMapping("vouchers/search")
    public String searchVoucherPage(){
        return "views/search-voucher";
    }

    @GetMapping("vouchers/delete")
    public String deleteVoucherPage(){
        return "views/delete-voucher";
    }

}
