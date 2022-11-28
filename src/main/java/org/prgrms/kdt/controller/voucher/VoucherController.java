package org.prgrms.kdt.controller.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.dto.AssignVoucherRequest;
import org.prgrms.kdt.model.voucher.dto.CreateVoucherRequest;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class VoucherController {

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

    @GetMapping("/vouchers/details/{voucherId}")
    public String voucherDetailsPage(Model model, @PathVariable String voucherId) {
        Voucher findVoucher = voucherService.findVoucherById(voucherId);
        model.addAttribute("voucher", findVoucher);
        return "/voucher/voucher_details";
    }

    @DeleteMapping("/vouchers")
    public String voucherRemovePage(String voucherId) {
        voucherService.removeVoucher(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/new-voucher")
    public String newVoucherPage() {
        return "voucher/new-voucher";
    }

    @PostMapping("/new-voucher")
    public String newVoucher(CreateVoucherRequest request) {
        voucherService.create(request.voucherType(), Double.toString(request.discountAmount()));
        return "redirect:/vouchers";
    }

    @PostMapping("customers/voucher_wallet")
    public String customerVoucherWalletPage(Model model, String wallet_customerId) {
        List<Voucher> ownedVouchers = voucherService.getOwnedVouchers(wallet_customerId);
        model.addAttribute("ownedVouchers", ownedVouchers);
        return "/customer/voucher-wallet";
    }

    @DeleteMapping("customers/voucher_wallet")
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
