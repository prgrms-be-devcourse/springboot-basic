package org.prgrms.kdt.controller;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerList;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherList;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.VoucherWalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class VoucherWalletController {

    private final VoucherWalletService voucherWalletService;

    private final VoucherService voucherService;

    private final CustomerService customerService;

    public VoucherWalletController(VoucherWalletService voucherWalletService, VoucherService voucherService, CustomerService customerService) {
        this.voucherWalletService = voucherWalletService;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @GetMapping("/voucherWallet")
    public String showVoucherList(Model model) {
        VoucherList voucherWalletList = voucherWalletService.getVoucherWalletList();
        List<Voucher> voucherWallet = voucherWalletList.getVouchers();
        model.addAttribute("voucherWallet", voucherWallet);
        return "voucherWallet/voucherWalletList";
    }

    @GetMapping("/walletHome")
    public String walletHome() {
        return "voucherWallet/walletHome";
    }

    @GetMapping("/voucherWallet/ownableList")
    public String showOwnableVoucherList(Model model) {
        VoucherList ownableVoucherList = voucherService.getOwnableVoucherList();
        List<Voucher> vouchers = ownableVoucherList.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucherWallet/ownableVoucherList";
    }

    @GetMapping("/voucherWallet/detail/{voucherId}")
    public String showVoucherWalletDetail(@PathVariable String voucherId, Model model) {
        Optional<Voucher> voucherDetail = voucherWalletService.getVoucherWalletById(UUID.fromString(voucherId));
        AtomicReference<String> returnPage = new AtomicReference<>("404Page");
        voucherDetail.ifPresent((voucher) -> {
            model.addAttribute("voucher", voucher);
            returnPage.set("voucherWallet/voucherWalletDetail");
        });
        return String.valueOf(returnPage);
    }

    @GetMapping("/voucherWallet/delete/{voucherId}")
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherService.deleteVoucherById(UUID.fromString(voucherId));
        return "/voucherWallet/walletHome";
    }

    @GetMapping("/voucherWallet/provideForm/{voucherId}")
    public String voucherProvideForm(Model model, @PathVariable String voucherId){
        CustomerList customerList = customerService.getAllCustomers();
        List<Customer> customers = customerList.getCustomers();

        model.addAttribute("customers", customers);
        model.addAttribute("voucherId", voucherId);
        return "voucherWallet/voucherProvideForm";
    }

    @PostMapping("/voucherWallet/provide")
    public String voucherProvide(@RequestParam Map<String, String> parameter) {
        String customerId = parameter.get("customerId");
        String voucherId = parameter.get("voucherId");
        System.out.println(customerId);
        System.out.println(voucherId);
        voucherService.provideVoucherToCustomer(voucherId, customerId);
        return "voucherWallet/walletHome";
    }
}
