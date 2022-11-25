package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.voucher.AssignVoucherRequest;
import org.prgrms.kdt.voucher.CreateVoucherRquest;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class VoucherController {

    private static final Logger log = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String voucherListsPage(Model model) {
        List<Voucher> allVouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", allVouchers);
        return "/voucher/voucher-lists";
    }

    @PostMapping("/vouchers")
    public String newVoucher(CreateVoucherRquest request) {
        voucherService.create(request.voucherType(), Double.toString(request.discountAmount()));
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/details")
    public String voucherDetailsPage(Model model, String voucherId) {
        Voucher findVoucher = voucherService.findVoucherById(voucherId);
        model.addAttribute("voucher", findVoucher);
        return "/voucher/voucher_details";
    }

    @GetMapping("/vouchers/remove")
    public String voucherRemovePage(String voucherId) {
        voucherService.removeVoucher(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/new-voucher")
    public String newVoucherPage() {
        return "voucher/new-voucher";
    }

    @PostMapping("customers/voucher_wallet")
    public String customerVoucherWalletPage(Model model, String wallet_customerId) {
        List<Voucher> ownedVouchers = voucherService.getOwnedVouchers(wallet_customerId);
        model.addAttribute("ownedVouchers", ownedVouchers);
        return "/customer/voucher-wallet";
    }

    @GetMapping("customers/voucher_wallet")
    public String customerOwnedVoucherRemovePage(String voucherId) {
        voucherService.removeAssignment(voucherId);
        return "redirect:/customers";
    }

    @PostMapping("/not-owned-lists")
    public String ownedVoucherPage(Model model, String owned_customerId) {
        List<Voucher> notOwnedVouchers = voucherService.getAllVouchers().stream()
                .filter(voucher -> voucher.getOwnedCustomerId().isEmpty())
                .collect(Collectors.toList());
        model.addAllAttributes(Map.of("vouchers", notOwnedVouchers,
                "customerId", UUID.fromString(owned_customerId)));
        return "/voucher/not-owned-lists";
    }

    @PostMapping("/owned-voucher")
    public String ownedVoucher(@ModelAttribute("assignVoucherRequest") AssignVoucherRequest assignVoucherRequest) {
        Voucher findVoucher = voucherService.findVoucherById(assignVoucherRequest.voucherId());
        voucherService.assignVoucher(findVoucher, UUID.fromString(assignVoucherRequest.customerId()));
        return "redirect:/customers";
    }
}
