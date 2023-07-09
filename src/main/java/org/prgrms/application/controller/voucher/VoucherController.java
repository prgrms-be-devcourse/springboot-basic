package org.prgrms.application.controller.voucher;

import org.prgrms.application.controller.voucher.request.VoucherCreationRequest;
import org.prgrms.application.domain.voucher.VoucherTypeFactory;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/voucher/")
public class VoucherController {

    private final VoucherTypeFactory voucherTypeFactory;
//    private VoucherService voucherService; //TODO: 수정 예정

    public VoucherController(VoucherTypeFactory voucherTypeFactory) {
        this.voucherTypeFactory = voucherTypeFactory;
    }

//    public List<Voucher> getStorage() { //TODO: 수정 예정
//        return voucherService.getVouchers();
//    }

    @GetMapping(value = "creation")
    public String getVoucherCreationForm() {
        return "voucherCreation";
    }

    @PostMapping(value = "creation")
    public String createVoucher(@ModelAttribute VoucherCreationRequest request) {
        VoucherService voucherService;
        voucherService = voucherTypeFactory.getVoucherService(request.getVoucherType()); // 어떤 서비스로 정보를 줄건지 결정 (fixed, percent 인지)
        System.out.println(request.getDiscountDetail());
        double voucherDetail = Double.parseDouble(request.getDiscountDetail()); //TODO: 불편함, 수정이 필요한 것 같지만 방법은 아직 모르겠음
        voucherService.createVoucher(voucherDetail);
        return "redirect:/";
    }

//    @GetMapping(value = "/vouchers")
//    public String findVouchers(Model model) {
//        List<Voucher> vouchers = voucherService.getVouchers(); //TODO: 이부분 수정 필수!
//        model.addAttribute("serverTime", LocalDateTime.now());
//        model.addAttribute("vouchers", vouchers);
//        return "views/vouchers";
//    }


}