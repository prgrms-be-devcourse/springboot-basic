package org.prgrms.kdt.wallet;

import org.prgrms.kdt.customer.CustomerDto;
import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.validate.VoucherFormValidator;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by yhh1056
 * Date: 2021/09/09 Time: 8:18 오후
 */
@Controller
public class WalletController {

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;

    public WalletController(VoucherService voucherService, CustomerService customerService, WalletService walletService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
    }

    @GetMapping("/admin/wallet")
    public String getWallet(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "admin/wallet";
    }

    @GetMapping("/admin/wallet/{customerId}")
    public String choiceVoucher(@PathVariable String customerId, Model model) {
        CustomerDto customerDto = customerService.getCustomerById(customerId);
        if (customerDto == null) {
            return "error";
        }

        model.addAttribute("customerId", customerId);
        model.addAttribute("vouchers", voucherService.getAllVouchers());
        model.addAttribute(new WalletDto());
        return "admin/wallet/form";
    }

    @PostMapping("/admin/wallet")
    public String submitWallet(WalletDto walletDto, RedirectAttributes attributes) {
        walletService.addWallet(walletDto);
        attributes.addFlashAttribute("message", "정상적으로 등록되었습니다.");
        return "redirect:/admin";
    }

}
