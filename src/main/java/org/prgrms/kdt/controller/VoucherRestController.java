package org.prgrms.kdt.controller;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherRestController {
    private final VoucherService voucherService;


    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public Map<UUID, Voucher> getVoucherList() {
        VoucherMap voucherList = voucherService.getVoucherList();
        return voucherList.getVouchers();
    }

    @GetMapping("/api/v1/voucher/id/{voucherId}")
    @ResponseBody
    public Optional<Voucher> getVoucherByID(@PathVariable String voucherId) {
        return voucherService.getVoucherById(UUID.fromString(voucherId));
    }

    @DeleteMapping("/api/v1/voucher/id/{voucherId}")
    @ResponseBody
    public String deleteVoucherById(@PathVariable String voucherId) {
        voucherService.deleteVoucherById(UUID.fromString(voucherId));
        return "delete";
    }

    @GetMapping("/api/v1/voucher/new/{voucherType}/{discountAmount}")
    @ResponseBody
    public Voucher createVoucher(@PathVariable long discountAmount, @PathVariable int voucherType){
        return voucherService.createVoucher(UUID.randomUUID(), voucherType, discountAmount);
    }
}
