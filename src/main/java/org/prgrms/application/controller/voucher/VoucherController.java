package org.prgrms.application.controller.voucher;

import org.prgrms.application.controller.voucher.request.VoucherGenerateRequest;
import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

//    public List<Voucher> getStorage() {
//        return voucherService.getVouchers();
//    }

    @GetMapping(value = "/generate")
    public String getVoucherCreationForm() {
        return "voucherCreation";
    }

    @PostMapping(value = "/generate")
    public String createVoucher(@ModelAttribute VoucherGenerateRequest request) {
        voucherService.createVoucher(VoucherType.valueOf(request.voucherType()), request.discountAmount()); //여기서 voucherType으로 객체 감싸주기
        return "redirect:/";
    }

    @GetMapping(value = "/")
    public String findVouchers(Model model) {
        List<Voucher> vouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers";
    }

    @GetMapping(value = "vouchers/{id}") //TODO : deletemappipng으로 안되는 중 <a태그 한계>
    public String deleteVoucher(@PathVariable("id") long voucherId){
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }

}