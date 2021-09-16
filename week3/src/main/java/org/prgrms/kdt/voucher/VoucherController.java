package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.model.CreateVoucherRequest;
import org.prgrms.kdt.voucher.service.IVoucherJdbcService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class VoucherController {

    private final IVoucherJdbcService voucherService;

    public VoucherController(IVoucherJdbcService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String viewCustomersPage(Model model) {
        var allVouchers = voucherService.list();
        model.addAttribute("vouchers", allVouchers);
        return "vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewNewCustomerPage() {
        return "/new-vouchers";
    }

    @PostMapping("/vouchers")
    public String newVoucher(CreateVoucherRequest createVoucherRequest) {
        voucherService.create(
                createVoucherRequest.walletId(),
                createVoucherRequest.voucherType(),
                createVoucherRequest.amount()
        );
        return "redirect:/customers/" + createVoucherRequest.customerId();
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

    @PostMapping("/vouchers/delete")
    public String deleteByVoucherId(@RequestParam("voucherId") UUID voucherId, @RequestParam("customerId") String customerId){
        voucherService.deleteById(voucherId);
        return "redirect:/customers/" + customerId;
    }
}
