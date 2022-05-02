package org.prgrms.kdt.controller;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.Vouchers;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
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

    @GetMapping("/api/v1/vouchers/voucherType/{voucherType}")
    @ResponseBody
    public List<Voucher> getVouchersByVoucherType(@PathVariable int voucherType) {
        Vouchers vouchers = voucherService.getVoucherListByVoucherType(voucherType);
        return vouchers.getVouchers();
    }

    @GetMapping("/api/v1/voucher/date/{fromDate}/{toDate}")
    @ResponseBody
    public List<Voucher> createVoucher(@PathVariable String fromDate, @PathVariable String toDate){
        Vouchers vouchers = voucherService.getVoucherListByCreatedFromToDate(fromDate, toDate);
        return vouchers.getVouchers();
    }
}
