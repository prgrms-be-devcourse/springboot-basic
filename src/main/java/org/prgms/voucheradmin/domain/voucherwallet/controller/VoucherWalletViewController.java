package org.prgms.voucheradmin.domain.voucherwallet.controller;

import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.customer.service.CustomerService;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.prgms.voucheradmin.domain.voucherwallet.dto.VoucherWalletReqDto;
import org.prgms.voucheradmin.domain.voucherwallet.service.VoucherWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherWalletViewController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherWalletViewController.class);

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final VoucherWalletService voucherWalletService;

    public VoucherWalletViewController(CustomerService customerService, VoucherService voucherService, VoucherWalletService voucherWalletService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.voucherWalletService = voucherWalletService;
    }

    @ExceptionHandler(Exception.class)
    public String except(Exception ex, Model model) {
        logger.error(ex.getMessage());
        model.addAttribute("message", ex.getMessage());
        return "views/error";
    }

    @PostMapping("/voucher-wallets/customers/{customerId}")
    public String addNewVoucherWallet(@PathVariable UUID customerId, VoucherWalletReqDto voucherWalletReqDto) {
         voucherWalletService.createVoucherWallet(customerId, voucherWalletReqDto.getVoucherId());
        return "redirect:/voucher-wallets/customers/"+customerId;
    }

    @GetMapping("/voucher-wallets")
    public String viewVoucherWalletMainPage(Model model) {
        List<Customer> allCustomers = customerService.getCustomers();
        model.addAttribute("customers", allCustomers);

        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", allVouchers);

        return "views/voucher-wallet/voucher-wallet-main";
    }

    @GetMapping("/voucher-wallets/customers/{customerId}")
    public String viewAllocatedVouchersPage(@PathVariable UUID customerId, Model model) {
        List<Voucher> allocatedVouchers = voucherWalletService.getAllocatedVouchers(customerId);
        model.addAttribute("customerId", customerId);
        model.addAttribute("allocatedVouchers", allocatedVouchers);

        return "views/voucher-wallet/allocated-vouchers";
    }

    @GetMapping("/voucher-wallets/customers/{customerId}/new")
    public String viewNewVoucherWalletPage(@PathVariable UUID customerId, Model model) {
        Customer customer = customerService.getCustomer(customerId);
        model.addAttribute("customer", customer);

        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", allVouchers);

        return "views/voucher-wallet/new-voucher-wallet";
    }

    @GetMapping("/voucher-wallets/vouchers/{voucherId}")
    public String viewVoucherOwnerPage(@PathVariable UUID voucherId, Model model) {
        List<Customer> owners = voucherWalletService.getVoucherOwners(voucherId);
        model.addAttribute("voucherId", voucherId);
        model.addAttribute("customers", owners);

        return "views/voucher-wallet/voucher-owner";
    }

    @PostMapping("/voucher-wallets/customers/{customerId}/delete/{voucherId}")
    public String deleteAllocatedVoucher(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
        voucherWalletService.deleteVoucherWallet(customerId, voucherId);
        return "redirect:/voucher-wallets/customers/"+customerId;
    }

}
