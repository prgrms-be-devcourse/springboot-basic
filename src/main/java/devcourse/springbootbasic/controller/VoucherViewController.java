package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherUpdateDiscountValueRequest;
import devcourse.springbootbasic.dto.wallet.VoucherAssignRequest;
import devcourse.springbootbasic.service.VoucherService;
import devcourse.springbootbasic.service.WalletService;
import devcourse.springbootbasic.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherViewController {

    private final VoucherService voucherService;
    private final WalletService walletService;

    @GetMapping
    public String findAllVouchers(Model model) {
        List<VoucherFindResponse> allVouchers = this.voucherService.findAllVouchers();
        model.addAttribute("vouchers", allVouchers);

        return "voucher/voucher-list";
    }

    @GetMapping("/create")
    public String create() {
        return "voucher/voucher-form";
    }

    @PostMapping("/create")
    public String createVoucher(@ModelAttribute VoucherCreateRequest voucherCreateRequest) {
        this.voucherService.createVoucher(voucherCreateRequest);
        return "redirect:/voucher";
    }

    @PostMapping("/{id}/delete")
    public String deleteVoucher(@PathVariable("id") String voucherId) {
        voucherService.deleteVoucher(UUIDUtil.stringToUUID(voucherId));

        return "redirect:/voucher";
    }

    @GetMapping("/{id}")
    public String showVoucherDetail(@PathVariable("id") String voucherId, Model model) {
        Voucher voucher = voucherService.getVoucherById(UUIDUtil.stringToUUID(voucherId));
        model.addAttribute("voucher", new VoucherFindResponse(voucher));

        return "voucher/voucher-detail";
    }

    @PostMapping("/{id}/updateDiscountValue")
    public String updateDiscountValue(
            @PathVariable("id") String voucherId,
            @RequestParam("discountValue") long newDiscountValue
    ) {
        voucherService.updateDiscountValue(UUIDUtil.stringToUUID(voucherId), new VoucherUpdateDiscountValueRequest(newDiscountValue));

        return "redirect:/voucher/" + voucherId;
    }

    @PostMapping("/{id}/assignCustomerId")
    public String assignCustomerId(
            @PathVariable("id") String voucherId,
            @RequestParam("customerId") UUID customerId
    ) {
        walletService.assignVoucherToCustomer(UUIDUtil.stringToUUID(voucherId), new VoucherAssignRequest(customerId));

        return "redirect:/voucher/" + voucherId;
    }

    @PostMapping("/{id}/unassignCustomerId")
    public String unassignCustomerId(@PathVariable("id") String voucherId) {
        walletService.unassignVoucherFromCustomer(UUIDUtil.stringToUUID(voucherId));

        return "redirect:/voucher/" + voucherId;
    }
}
