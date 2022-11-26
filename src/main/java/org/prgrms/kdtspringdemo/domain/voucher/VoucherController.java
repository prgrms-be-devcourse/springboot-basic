package org.prgrms.kdtspringdemo.domain.voucher;

import org.prgrms.kdtspringdemo.domain.voucher.model.CreateVoucherRequest;
import org.prgrms.kdtspringdemo.domain.voucher.model.VoucherType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public String VouchersPage(Model model) {
        var vouchers = voucherService.getAllVoucherList();
        model.addAttribute("vouchers", vouchers);
        return "vouchers";
    }

    @GetMapping("/{voucherId}")
    public String VoucherDetailPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        var voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher-detail";
    }

    @GetMapping("/new")
    public String createVoucherPage(Model model) {
        var voucherTypes = VoucherType.values();
        model.addAttribute("voucherTypes", voucherTypes);
        return "voucher-new";
    }

    @PostMapping("/new")
    public String CreateVoucher(Model model, CreateVoucherRequest createVoucherRequest) {
        try {
            voucherService.createVoucher(VoucherType.getTypeByName(createVoucherRequest.voucherType()),
                    createVoucherRequest.amountOrPercent());
        } catch (Exception e) {
            e.getStackTrace();
            model.addAttribute("createVoucherFail", true);
            return createVoucherPage(model);
        }

        return "redirect:/vouchers";
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Object> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
