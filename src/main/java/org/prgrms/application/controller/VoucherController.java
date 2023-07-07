package org.prgrms.application.controller;

import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.domain.voucher.VoucherTypeFactory;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class VoucherController {

    private final VoucherTypeFactory voucherTypeFactory;

    public VoucherController(VoucherTypeFactory voucherTypeFactory) {
        this.voucherTypeFactory = voucherTypeFactory;
    }

//    public List<Voucher> getStorage() {
//        VoucherService voucherService;
//        return voucherService.getVouchers();
//    }

    public void createVoucher(VoucherType voucherType, double discountAmount) {
        VoucherService voucherService;
        voucherService = voucherTypeFactory.getType(voucherType); // 어떤 서비스로 정보를 줄건지 결정 (fixed, percent인지)
        voucherService.createVoucher(voucherType, discountAmount); // 나는 그냥 바우처를 만들어 라고 명령만! : (어디에 할지는 자동으로 26번쨰줄에서 결정이되었음)
    }

    @GetMapping(value = "/vouchers")
    public String findVouchers(Model model,VoucherType voucherType) {
        VoucherService voucherService;
        voucherService = voucherTypeFactory.getType(voucherType);
        List<Voucher> vouchers = voucherService.getVouchers();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("vouchers", vouchers);
        return "views/vouchers";
    }
}