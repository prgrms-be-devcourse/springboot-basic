package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.model.CreateVoucherRequest;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class VoucherController {

    private final static Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping("/vouchers")
    public String viewCustomersPage(Model model) {
        var allVouchers = voucherService.list();
        model.addAttribute("vouchers", allVouchers);
        return "vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewNewCustomerPage(Model model) {
        return "/new-vouchers";
    }

    @PostMapping("/vouchers")
    public String newVoucher(CreateVoucherRequest createVoucherRequest) {
        voucherService.create(
                createVoucherRequest.voucherType(),
                createVoucherRequest.amount()
        );
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String findCustomer(@PathVariable("voucherId") UUID voucherId, Model model) {
        var mayBeVoucher = voucherService.getVoucher(voucherId);
        if(mayBeVoucher.isPresent()){
            model.addAttribute("voucher", mayBeVoucher.get());
            return "voucher-details";
        }else{
            return "/404";
        }
    }

    @PostMapping("/vouchers/delete/{voucherId}")
    public String deleteByVoucherId(@PathVariable("voucherId") UUID voucherId){
        logger.info("voucherId:{}", voucherId);
        voucherService.deleteById(voucherId);
        return "redirect:/vouchers";
    }
}
